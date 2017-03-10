package com.ericsson.scala

object RangeSumup {
  def totalResultOverRange(number: Int, codeBlock: Int => Int): Int = {
    var result = 0
    for (i <- 1 to number)
      result += codeBlock(i)
    result
  }

  def main(args: Array[String]): Unit = {
    println(totalResultOverRange(100, i => if (i % 2 == 0) i else 0))
    println(totalResultOverRange(100, i => if (i % 2 != 0) i else 0))
  }
}