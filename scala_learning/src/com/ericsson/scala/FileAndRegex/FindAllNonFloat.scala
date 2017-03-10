package com.ericsson.scala.FileAndRegex

import scala.io.Source

object FindAllNonFloat extends App {
  val text = Source.fromFile("file/float.txt").mkString.split("\\s+|,\\s*")
  val pattern = "[-+]?[0-9]*\\.?[0-9]*"
  println(text.filter { !_.matches(pattern) }.mkString(" "))
}