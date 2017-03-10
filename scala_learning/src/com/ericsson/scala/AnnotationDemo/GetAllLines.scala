package com.ericsson.scala.AnnotationDemo

import scala.io.Source

class GetAllLines {
  def getAllLineFromFile() = {
    Source.fromFile("word_statistic.txt").getLines().mkString("\n")
  }
}