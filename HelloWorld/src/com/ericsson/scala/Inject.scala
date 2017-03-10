package com.ericsson.scala

object Inject {
  def inject(arr: Array[Int], initial: Int, operation: (Int, Int) => Int): Int = {
    var carryover = initial
    arr.foreach(element => carryover = operation(carryover, element))
    carryover
  }

  def inject2(arr: Array[Int], initial: Int)(operation: (Int, Int) => Int): Int = {
    var carryover = initial
    arr.foreach(element => carryover = operation(carryover, element))
    carryover
  }

  def main(args: Array[String]): Unit = {
    val array = Array(2, 3, 4, 5, 6)
    val sum = inject(array, 0, (carryover, elem) => carryover + elem)
    println("Sum of elements in array " + array.toString() + " is " + sum)

    val max = inject(array, Integer.MIN_VALUE, (carryover, elem) => Math.max(carryover, elem))
    println("max elements in array " + array.toString() + " is " + max)

    val max2 = inject2(array, Integer.MIN_VALUE) { Math.max }
    println("max elements in array " + array.toString() + " is " + max2)

    val array2 = Array(2, 3, 4, 5, 6)
    val sum3 = (0 /: array2) { (sum, elem) => sum + elem }
    println("Sum of elements in array2 " + array2.toString() + " is " + sum3)
    val max4 = (Integer.MIN_VALUE /: array) {
      (large, elem) => Math.max(large, elem)
    }
    println("max elements in array2 " + array2.toString() + " is " + max4)
  }
}