package com.ericsson.scala.Object

class Origin(private val xx: Int, private val yy: Int) extends java.awt.Point(xx, yy) {
  override def toString = {
    "Origin:x=" + xx + " y=" + yy
  }
}

object Origin extends App {
  def apply(x: Int, y: Int) = new Origin(x, y)

  println(Origin(1, 2))
}