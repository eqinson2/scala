package com.ericsson.scala.FileAndRegex

import scala.io.Source

object reverseFile extends App {
  val source = Source.fromFile("word_statistic.txt")
  source.getLines().toArray.reverse.foreach {
    println _
  }
  source.close()
}