package com.ericsson.scala.Matches

object compose extends App {
  val f = (x: Double) => if (x >= 0) Some(Math.sqrt(x)) else None
  val g = (x: Double) => if (x != 1) Some(1 / (x - 1)) else None

  def compose(f: (Double) => Option[Double], g: (Double) => Option[Double]) = {
    (x: Double) =>
      if (f(x).isEmpty || g(x).isEmpty) None
      else Some(f(x).get * g(x).get)
  }

  val h = compose(f, g)
  print(h(2))
}