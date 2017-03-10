package com.ericsson.scala.Implicit

class Fraction3(val num: Int, val den: Int) {
  def *(other: Fraction3) = new Fraction3(num * other.num, den * other.den)
  override def toString = num + "/" + den
}

object Fraction3 {
  def apply(num: Int, den: Int) = new Fraction3(num, den)
}

object FractionCompareTest3 extends App {
  def smaller[T](a: T, b: T)(implicit order: T => Ordered[T]) = if (a < b) a else b

  implicit def Fraction2Ordered(fract: Fraction3) = {
    new Ordered[Fraction3] {
      override def compare(that: Fraction3) = fract.num / fract.den - that.num / that.den
    }
  }

  print(smaller(Fraction3(1, 7), Fraction3(2, 9)))
}