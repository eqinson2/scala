package com.ericsson.scala.ClassExtends

abstract class Shape(protected val descrp: String) {
  def centerPoint: Point
}

class Circle(center: Point, r: Int) extends Shape("This is a Circle") {
  print(descrp)
  def centerPoint = { center }
}

class Rectangle(upperLeft: Point, bottomRight: Point) extends Shape("This is a Rectangle") {
  print(descrp)
  def centerPoint = { new Point((upperLeft.x + bottomRight.x) / 2, (upperLeft.y + bottomRight.y) / 2) }
}