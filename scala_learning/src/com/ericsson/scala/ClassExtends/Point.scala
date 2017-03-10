package com.ericsson.scala.ClassExtends

class Point(val x: Int, val y: Int) {
}

class LabeldPoint(x: Int, y: Int, val label: String) extends Point(x, y) {
}

object PointTest extends App {
  val lp = new LabeldPoint(1, 2, "eqinson")
  println(lp.x, lp.y, lp.label)
}