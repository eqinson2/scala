package com.ericsson.scala.Implicit

import java.awt.Point
import scala.util.Sorting

object PointOrdering extends Ordering[Point] {
  def compare(a: Point, b: Point) = {
    val delta = a.x compare b.x
    if (delta == 0) a.y compare b.y
    else delta
  }
}

object PointDistanceOrdering extends Ordering[Point] {
  def compare(a: Point, b: Point) = {
    a.x * a.x + a.y * a.y compare b.x * b.x + b.y * b.y
  }
}

object PointTest extends App {
  val points = Array(new Point(5, 6), new Point(1, 2), new Point(2, 4))
  Sorting.quickSort(points)(PointOrdering)
  points.foreach { println _ }

  println
  
  val points2 = Array(new Point(5, 6), new Point(1, 2), new Point(2, 4))
  Sorting.quickSort(points2)(Ordering[(Int, Int)].on(p => (p.x, p.y)))
  points2.foreach { println _ }

  println
  
  val points3 = Array(new Point(5, 6), new Point(1, 2), new Point(2, 4))
  Sorting.quickSort(points3)(PointDistanceOrdering)
  points3.foreach { println _ }
}