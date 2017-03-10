package com.ericsson.scala.CollectionUsage

object MyMkstring extends App {
  def myMkString(seq: Seq[String])(delimer: String) = {
    seq.reduceLeft(_ + delimer + _)
  }

  val array = Array("i", "am", "engineer")
  println(myMkString(array)(" "))
  println(myMkString(array)("\n"))
}