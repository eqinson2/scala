package com.ericsson.scala.TraitsDemo

import java.awt.geom.Ellipse2D

trait RectangleLike {
  this: Ellipse2D.Double =>
  def translate(x: Double, y: Double): Unit = {
    this.x = x
    this.y = y
  }

  def grow(x: Double, y: Double): Unit = {
    this.x += x
    this.y += y
  }
}

object Test2 extends App {
  val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
  egg.translate(10, -10)
  egg.grow(10, 20)
}