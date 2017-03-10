package com.ericsson.scala.Implicit

class Fraction(val num: Int, val den: Int) extends Ordered[Fraction] {
  def *(other: Fraction) = new Fraction(num * other.num, den * other.den)
  override def compare(that: Fraction) = num / den - that.num / that.den
  override def toString = num + "/" + den
}

object Fraction {
  def apply(num: Int, den: Int) = new Fraction(num, den)
}

object FractionCompareTest extends App {
  def smaller[T](a: T, b: T)(implicit order: T => Ordered[T]) = if (a < b) a else b

  print(smaller(Fraction(1, 7), Fraction(2, 9)))
}