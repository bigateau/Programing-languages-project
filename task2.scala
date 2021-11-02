object Hello extends App{
	println("Hello World")
}

object Task2A extends App{

	def Hi(): Unit = println("Hello World")
	
	def Func_to_Thread(func: () => Unit): Thread = {
		val t = new Thread {
			override def run() = func
	 	}
	 	return t
	}

	val th = Func_to_Thread(Hi)
}

object Task2B extends App{

	private var counter: Int = 0
	def increaseCounter(): Unit = {
		counter += 1
	}

	def Func_to_Thread(func: () => Unit): Thread = {
		val t = new Thread {
			override def run() = func
	 	}
	 	return t
	}
	val thread1 = Func_to_Thread(increaseCounter)
    thread1.start

    /*val thread2 = new Thread {
        override def run {
            // your custom behavior here
        }
    }
    thread2.start*/

    val thread3 = new Thread {
        override def run {
            println(counter)
        }
    }
    thread3.start
}