package com.ericsson.scala.FileAndRegex

import scala.io.Source

object findSrc extends App {
  val pattern = """src="(http://[a-zA-Z./]+)"""".r
  for (pattern(url) <- pattern.findAllIn(Source.fromURL("http://www.sina.com.cn/").mkString))
    println(url)
}