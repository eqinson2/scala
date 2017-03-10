package com.ericsson.scala.Operator

class Money(val dollar: Int, val cent: Int) {
  def +(other: Money): Money = {
    if (cent + other.cent >= 100) new Money(dollar + other.dollar + 1, cent + other.cent - 100)
    else new Money(dollar + other.dollar, cent + other.cent)
  }
  def -(other: Money) = {}
  def ==(other: Money): Boolean = { dollar == other.dollar && cent == other.cent }
  def <(other: Money): Boolean = { dollar < other.dollar || dollar == other.dollar && cent < other.cent }
  override def toString = { s"$$ ${dollar}.${cent}" }
}

object Money extends App {
  def apply(dollar: Int, cent: Int) = new Money(dollar, cent)
  
  println(Money(1, 20) + Money(2, 50))
  println(Money(1, 50) + Money(2, 50))
  println(Money(1, 20) < Money(2, 50))
  println(Money(2, 20) == Money(2, 50))
}