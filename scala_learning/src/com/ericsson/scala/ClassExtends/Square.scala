package com.ericsson.scala.ClassExtends

class Square(x: Int, y: Int, width: Int) extends java.awt.Rectangle(x, y, width, width) {
  def this(x: Int, y: Int) {
    this(x, y, 0)
  }

  def this() {
    this(0, 0, 0)
  }
}