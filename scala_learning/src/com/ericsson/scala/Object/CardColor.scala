package com.ericsson.scala.Object

object Card extends Enumeration with App {
  val M = Value("♣")
  val T = Value("♠")
  val H = Value("♥")
  val F = Value("♦")

  var current: Card.Value = M

  println(M)
  println(T)
  println(H)
  println(F)

  def color(c: Card.Value) = {
    if (c == M || c == T)
      println("Black")
    else
      println("Red")
  }

  color(current)
  current = H
  color(current)
}