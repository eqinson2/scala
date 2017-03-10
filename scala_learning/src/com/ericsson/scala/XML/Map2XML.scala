package com.ericsson.scala.XML

import scala.xml.XML

object XMap2XML extends App {
  val map = Map("A" -> "1", "B" -> "2", "C" -> 3)
  println(<dl>{ map.map(t => <dt>{ t._1 }</dt><dd>{ t._2 }</dd>) }</dl>)
}