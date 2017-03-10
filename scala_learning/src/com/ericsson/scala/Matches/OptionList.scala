package com.ericsson.scala.Matches

object OptionList extends App {
  def sumOfOptionList(lst: List[Option[Int]]) = {
    (0 /: lst)((a, b) => a + b.getOrElse(0))
  }
}