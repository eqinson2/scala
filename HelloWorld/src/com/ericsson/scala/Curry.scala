package com.ericsson.scala

object Curry {
  def inject(arr: Array[Int], initial: Int)(operation: (Int, Int) => Int): Int = {
    var carryover = initial
    arr.foreach(element => carryover = operation(carryover, element))
    carryover
  }

  def main(args: Array[String]): Unit = {
    val array = Array(2, 3, 5, 1, 6, 4)
    val sum = inject(array, 0) { (carryover, elem) => carryover + elem }
    println("Sum of elements in array " + array.toString() + " is " + sum)
  }
}