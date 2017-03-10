package com.ericsson.scala.Implicit

class MyRichInt(val value: Int) {
  def ! = Util.factorial(value)
}

object Util {
  def factorial(v: Int): Int = {
    v match {
      case 0 | 1 => 1
      case _     => v * factorial(v - 1)
    }
  }
}

object MyRichInt {
  def apply(value: Int) = new MyRichInt(value)
}

object MyRichIntConversion {
  implicit def Int2MyRichInt(value: Int) = MyRichInt(value)
}

object Problem3 extends App {
  import MyRichIntConversion._
  println(12 !)
}