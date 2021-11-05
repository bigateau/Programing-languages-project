
//Task 1 (a) Generate an array containing the values 1 up to 50 using a for Loop
object Loop1A extends App{
	var Array1 = new Array[Int](50)         //Declaring our Int Array
	for ( i <- 1 until 51) {                //Loop from 1 until 51
		Array1(i - 1) = i                     //Assigning each value, the array begins at 0 so we use an offset
	}
	//Display
	for ( j <- 0 to (Array1.length - 1)) {
		println(Array1(j))
	}
}

//Task 1 (b) Generate a function that sums the elements in an array of integers using a for loop
object Loop1B extends App{
  //See Task 1 for generating an Array
	var Array2 = new Array[Int](50)         
	for ( i <- 1 until 51) {                
		Array2(i - 1) = i
	}
	//Function sum with an Integer as output which is the total
	def sum(Tab: Array[Int]) : Int = 
	{
      var Total=0;                      //Initializing our Total variable
      for(j <- 0 to (Tab.length - 1)){  //Going through each value from the input Array
        Total += Tab(j);                //Adding each element from the array into the Total variable
      }
      return Total                      //Output 
  }
	println(sum(Array2))
}

//Task 1 (c) Function that sums the elements in an array of integers using recursion
object Loop1C extends App{
  //See Task 1 for generating an Array
	var Array3 = new Array[Int](50)
	for ( i <- 1 until 51) {
		Array3(i - 1) = i
	}
	//Function sum with an Integer as output which is the total
	def recursion(Tab:Array[Int], index: Int): Int = 
	{
		if(index == Tab.length) {               //End of the recursion
			0                                     //Neutral value
		}
		else {                                  //Case of recursion
			Tab(index) + recursion(Tab, index+1)  //Returning the value from the current index + the value 
			                                      //from the next recursions
		}
	}
	println(recursion(Array3, 0))
}

//Task 1 (d) Function to compute the nth Fibonacci using recursion
object Loop1D extends App{
  //Function fibo with a BigInt as output
	def fibo(index: Int): BigInt = index match{  //Using the match/case given the index as input
	  case 0 => 0                                //Case 0 : neutral value, end the recursion
	  case 1 => 1                                //Case 1 : neutral value, does not call for any other recursion
	  case _ => fibo(index-2) + fibo(index-1)    //Recursion for any other cases, adding 
	                                             //the function until we reach 1 and 0 as indexes
	}
	println(fibo(0))
	println(fibo(1))
	println(fibo(2))
	println(fibo(3))
	println(fibo(4))
	println(fibo(5))
	println(fibo(6))
	println(fibo(7))
} //Using a BigInt is usefull they support all the operators that we can use with numeric types
  //It is also usefull because The BigInt takes 8 byte, the int is only 4 byte