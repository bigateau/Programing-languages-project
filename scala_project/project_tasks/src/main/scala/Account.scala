import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = {
      balance.synchronized {
        if ((amount > 0) && (amount < balance.amount)) {
          balance.amount = balance.amount - amount;
          Left(Unit)
        } else {
          Right("Le montant est négatif, ou, vous n'avez pas assez d'argent")
        }
      }
    }
    def deposit (amount: Double): Either[Unit, String] = {
      balance.synchronized {
        if (amount > 0) {
          balance.amount = balance.amount + amount;
          Left(Unit)
        } else {
          Right("Le montant est négatif, concentrez-vous")
        }
      }
    }
    def getBalanceAmount: Double = {
      balance.synchronized {
        balance.amount
      }
    }

    def transferTo(account: Account, amount: Double) = {
        bank addTransactionToQueue (this, account, amount)
    }


}
