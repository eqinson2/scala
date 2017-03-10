package com.ericsson.scala.AnnotationDemo

object AssertTest {
  def factorial(n: Int): Int = {
    assert(n > 0)
    n
  }

  def main(args: Array[String]) {
    factorial(-1)
  }
}