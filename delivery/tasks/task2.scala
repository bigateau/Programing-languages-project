import java.util.concurrent.atomic.AtomicInteger

object ConcurrencyInScala extends App {

  // We use the class AtomicInteger in order to assure thread safety
  private var counter: AtomicInteger = new AtomicInteger(0);
  
  private lazy val A : Int = 0;
  private lazy val B : Int = A + 1;
  
  def increaseCounter(): Unit = {
      // The function getAndIncrement() makes the incrementation atomic,
      // which means that no other operation can be placed in between operations
      // of the addition, in other words, the program is kind of suspended while
      // the addition is computed.
      counter.getAndIncrement();
  }

  // Print the private and class-scope variable counter in the terminal
  def printCounter() : Unit = {
    println(counter);
  }

  // This function return a Thread variable which contains the function fun,
  // that has not been started yet
  def initializedThread(fun : () => Unit) : Thread = {
    val thread = new Thread(new RunnableFunction(fun));
    thread;
  }

  // This function locks A and then locks B after a little sleepy time
  def function_A_B() : Unit = {
    A.synchronized {
      println("Thread 1 locked A");
      Thread.sleep(500);
      println("Thread 1 wait to lock B");
      B.synchronized {
        println("Thread 1 has A and B locked");
      }
    }
  }

  // This function locks B and then locks A after a little sleepy time
  def function_B_A() : Unit = {
    B.synchronized {
      println("Thread 2 locked B");
      Thread.sleep(500);
      println("Thread 2 wait to lock A");
      A.synchronized {
        println("Thread 2 has A and B locked");
      }
    }
  }

  val incrementThread1 = initializedThread(increaseCounter);
  val incrementThread2 = initializedThread(increaseCounter);
  val printThread = initializedThread(printCounter);
  println(incrementThread1.isAlive()); // Check that the Thread is in runnable state and not running state
  incrementThread1.start;
  incrementThread2.start;
  printThread.start;

  // Deadlock happens here
  val thread_1 = initializedThread(function_A_B);
  val thread_2 = initializedThread(function_B_A);
  thread_1.start;
  thread_2.start;
  
}

class RunnableFunction(fun : () => Unit) extends Runnable {
// The class RunnableFunction create an object that is Runnable
// which we will pass to a Thread.
  def run() : Unit = {
    fun();
  }
}

/*
This kind kind of phenomenon can be called reader-writer problems, it can be really problematic
with bank account for example, if two transactions are ordered at the same time,
you want the right amount of money withdrawn or added to the account, with reader-writer problems
not handled, an operation could be over read by another, which is very problematic.


A deadlock happens when two separate threads wait for each other 
to do an action that is necessary for the other to continue. 
For example : 
  thread 1 locks variable A
  thread 1 is suspended
  thread 2 locks variable B
  thread 2 waits for thread 1 to unlock variable A
  thread 2 is suspended
  thread 1 waits for thread 2 to unlock variable B

Here we can see the loop going over and over since neither thread 1
nor thread 2 is going to give priority to the other to finish computation
to unlock the deadlock. 

To have a deadlock you need four conditions :
  Mutual exclusion
  Hold and Wait behaviour
  No preemption (a process cannot take away resource from an other)
  Circular wait

Hence to prevent deadlock to happen you at least need one of the following conditions :
  No mutual exclusion
  No Hold and wait behaviour
  Preemption (which leads to setting thread priority)
  No circular wait
*/