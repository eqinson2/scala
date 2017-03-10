package org.nicholasren.scala.stock

import java.net.URL
import scala.io.Source

object ReadingURL {
  def main(args: Array[String]) {
    val source = Source.fromURL(new URL("http://www.scala-lang.org/docu/files/api/index.html"))

    val content = source.mkString

    val VersionRegEx = """[\D\S]+scaladoc\s+\(version\s+(.+)\)[\D\S]+""".r

    content match {
      case VersionRegEx(version) => println("Scala doc for version : " + version)
    }

  }
}