package com.ericsson.scala.AdvType

object PrintValuesTest extends App {
  def printValues(f: { def apply(n: Int): Int }, from: Int, to: Int) = {
    (from to to).foreach { (n: Int) => print(f(n) + " ") }
  }

  printValues((x: Int) => x * x, 3, 6)
  println
  printValues(Array(1, 1, 2, 3, 5, 8, 13, 21, 34, 55), 3, 6)
}