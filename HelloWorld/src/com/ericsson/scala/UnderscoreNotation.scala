package com.ericsson.scala

object UnderscoreNotation {
  val array = Array(2, 3, 4, 5, 6)

  def max2(a: Int, b: Int): Int = if (a > b) a else b
  def max = (Integer.MIN_VALUE /: array) { max2 _ }

  def main(args: Array[String]): Unit = {
    val sum = (0 /: array) { _ + _ }
    println("Array3 has negative number? " + array.exists { _ < 0 })

    println("max value in array is " + max)

  }
}