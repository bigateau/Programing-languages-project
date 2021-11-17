import exceptions._
import scala.collection.mutable.Queue

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionsQueue {

    private var transactionsQueue : Queue[Transaction] = new Queue[Transaction]();

    // Remove and return the first element from the queue
    def pop : Transaction = {
      transactionsQueue.synchronized {
        transactionsQueue.dequeue
      }
    }

    // Return whether the queue is empty
    def isEmpty: Boolean = {
      transactionsQueue.synchronized {
        transactionsQueue.isEmpty
      }
    }

    // Add new element to the back of the queue
    def push(t: Transaction): Unit = {
      transactionsQueue.synchronized {
        transactionsQueue += t;
      }
    }

    // Return the first element from the queue without removing it
    def peek: Transaction = {
      transactionsQueue.synchronized {
        transactionsQueue.front
      }
    }

    // Return an iterator to allow you to iterate over the queue
    def iterator: Iterator[Transaction] = {
      transactionsQueue.synchronized {
        transactionsQueue.iterator;
      }
    }
}

class Transaction(val transactionsQueue: TransactionsQueue,
                  val processedTransactions: TransactionsQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttemps: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt = 0

  override def run: Unit = {

      def doTransaction() = {
          from.withdraw(amount);
          to.deposit(amount);
          status = TransactionStatus.SUCCESS;
      }

      // TODO - project task 3
      // make the code below thread safe
      if (status == TransactionStatus.PENDING) {
          doTransaction
          Thread.sleep(50) // you might want this to make more room for
                           // new transactions to be added to the queue
      }


    }
}
