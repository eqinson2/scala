package com.ericsson.scala.FileAndRegex

import scala.io.Source

object FilterLengthGt12 extends App {
  Source.fromFile("word_statistic.txt").mkString.split("\\s+|,\\s*|\\.\\s*").filter { s => s.length() > 12 }.map { println _ }
}