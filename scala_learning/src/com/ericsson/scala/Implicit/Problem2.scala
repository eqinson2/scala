package com.ericsson.scala.Implicit

class Percentage(val base: Int) {
  def +%(adder: Int) = {
    base + adder
  }
}

object Percentage {
  def apply(value: Int) = new Percentage(value)
}

object PercentageConversion {
  implicit def Int2Percentage(value: Int) = Percentage(value)
}

object Problem2 extends App {
  import PercentageConversion._
  println(300 +% 4)
}