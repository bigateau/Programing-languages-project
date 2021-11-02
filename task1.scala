object Loop1A extends App{
	var Array1 = new Array[Int](50)
	for ( i <- 1 until 51) {
		Array1(i - 1) = i
	}
	for ( j <- 0 to (Array1.length - 1)) {
		println(Array1(j))
	}
}

object Loop1B extends App{
	var Array2 = new Array[Int](50)
	var Total = 0
	for ( i <- 1 until 51) {
		Array2(i - 1) = i
	}
	for ( j <- 0 to (Array2.length - 1)) {
		Total += Array2(j)
	}
	println(Total)
}

object Loop1C extends App{
	var Array3 = new Array[Int](50)
	for ( i <- 1 until 51) {
		Array3(i - 1) = i
	}

	def recursion(Tab:Array[Int], index: Int): Int = 
	{
		if(index == Tab.length) {
			0
		}
		else {
			Tab(index) + recursion(Tab, index+1)
		}
	}
	println(recursion(Array3, 0))
}

object Loop1D extends App{
	def Fibo(index: Int): BigInt = {
	  if (index <= 0) {
	    0
	  } else if (index == 1) {
	    1
	  } else {
	    Fibo(index - 1) + Fibo(index - 2)
	  }
	}
	println(Fibo(0))
	println(Fibo(1))
	println(Fibo(2))
	println(Fibo(3))
	println(Fibo(4))
	println(Fibo(5))
	println(Fibo(6))
	println(Fibo(7))
}