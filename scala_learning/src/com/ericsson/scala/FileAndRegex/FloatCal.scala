package com.ericsson.scala.FileAndRegex

import scala.io.Source

object FloatCal extends App {
  val source = Source.fromFile("float.txt")
  val allNum = source.mkString.split("\\s+|,\\s*").filter {
    _.matches("[-+]?[0-9]*\\.?[0-9]*")
  }.map {
    _.toDouble
  }

  val sum = (0.0d /: allNum) ((a, b) => a + b)
  println(sum)

  println(sum / allNum.length)

  val max = (0.0d /: allNum) ((a, b) => Math.max(a, b))
  val min = (Double.MaxValue /: allNum) ((a, b) => Math.min(a, b))

  println(max)
  println(min)

  source.close()
}