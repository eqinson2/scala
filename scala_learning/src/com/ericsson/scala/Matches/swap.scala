package com.ericsson.scala.Matches

object swap extends App {
  def swap(t: (Int, Int)) = {
    t match {
      case (x, y) => (y, x)
    }
  }

  val s = swap((3, 4))
  println(s)

  def swap2(a: Array[Int]) = {
    if (a.length > 2) {
      a match {
        case Array(x, y, ar @ _*) => Array(y, x) ++ ar
      }
    } else a
  }

  val ss = swap2(Array(1, 2, 3, 4, 5, 6, 7, 8, 9))
  println(ss.mkString(" "))
}