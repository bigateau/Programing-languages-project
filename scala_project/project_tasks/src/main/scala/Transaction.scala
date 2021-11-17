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
                  val allowedAttempts: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING
  var attempt = 0

  override def run: Unit = {

    def doTransaction() = {
      var withdraw_success : Boolean = false;
      var deposit_success : Boolean = false;
      from.withdraw(amount) match {
        case Left(success) => withdraw_success = true;
        case Right(error) => {
          withdraw_success = false;
        }
      }
      if (withdraw_success == true) {
        to.deposit(amount) match {
          case Left(success) => deposit_success = true;
          case Right(error) => {
            withdraw_success = false;
          }
        }
      }
      if (withdraw_success && deposit_success) {
        status = TransactionStatus.SUCCESS;
      } /*else {
        status = TransactionStatus.FAILED;
      }*/
    }

    // TODO - project task 3
    // make the code below thread safe
    while (!(status == TransactionStatus.SUCCESS) && attempt < allowedAttempts) {
      doTransaction
      attempt = attempt + 1
      Thread.sleep(50) // you might want this to make more room for
                        // new transactions to be added to the queue
    }
    if (attempt == allowedAttempts) {
      status = TransactionStatus.FAILED;
    }

  }
}
