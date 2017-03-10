package com.ericsson.scala.Generic

object Middle extends App {
  def middle[T](it: Iterable[T]) = {
    val pos = if (it.size % 2 == 0) it.size / 2 - 1 else it.size / 2
    if (pos < 0) it.toIndexedSeq(0)
    else it.toIndexedSeq(pos)
  }

  println(middle("World"))
}