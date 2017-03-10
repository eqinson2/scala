package com.ericsson.scala

object Closure {
  def loopThrough(number: Int)(closure: Int => Unit) {
    for (i <- 1 to number) { closure(i) }
  }
  def main(args: Array[String]): Unit = {
    var result = 10
    loopThrough(10) { value: Int => result += value }
    println("Total of values from 1 to 10 is " + result)
    
    loopThrough(10) { value: Int => result += value }
    println("Total of values from 1 to 10 is " + result)

    var product = 1
    loopThrough(5) { value: Int => product *= value }
    println("Product of values from 1 to 10 is " + product)
  }
}