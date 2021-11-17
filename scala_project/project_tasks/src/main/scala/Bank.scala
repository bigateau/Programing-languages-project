class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionsQueue = new TransactionsQueue()
    private val processedTransactions: TransactionsQueue = new TransactionsQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
      var t : Transaction = new Transaction(transactionsQueue,
                                            processedTransactions,
                                            from,
                                            to,
                                            amount,
                                            allowedAttempts)
      transactionsQueue.push(t);
      val transactionThread : Thread = new Thread(t);
      transactionThread.start;
      processTransactions;
    }
                                                

    private def processTransactions: Unit = {
      var transactionIter : Iterator[Transaction] = transactionsQueue.iterator;
      for (t <- transactionIter) {
        var transactionPending : Transaction = transactionsQueue.pop;
        if (t.status == TransactionStatus.PENDING) {
          transactionsQueue.push(transactionPending);
          processedTransactions;
        } else {
          processedTransactions.push(transactionPending);
        }
      }
    }
                                                // TOO
                                                // project task 2
                                                // Function that pops a transaction from the queue
                                                // and spawns a thread to execute the transaction.
                                                // Finally do the appropriate thing, depending on whether
                                                // the transaction succeeded or not

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
