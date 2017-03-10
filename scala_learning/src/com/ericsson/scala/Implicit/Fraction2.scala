package com.ericsson.scala.Implicit

class Fraction2(val num: Int, val den: Int) {
  override def toString = num + "/" + den
}

object Fraction2 {
  def apply(num: Int, den: Int) = new Fraction2(num, den)
}

class Pair[T: Ordering](val first: T, val second: T) {
  def smaller = {
    println(implicitly[Ordering[T]]);
    if (implicitly[Ordering[T]].compare(first, second) < 0) first else second
  }
}

object FractionCompareTest2 extends App {
  implicit object Fraction2Ordering extends Ordering[Fraction2] {
    override def compare(x: Fraction2, y: Fraction2) = x.num / x.den compare y.num / y.den
  }

  val p = new Pair(Fraction2(1, 7), Fraction2(1, 9))
  print(p.smaller)
}