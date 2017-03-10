package org.nicholasren.scala.stock

import java.io._

object WriteToFile {
  def main(args: Array[String]) {
    val writer = new PrintWriter(new File("symbols.txt"))
    writer write "AAPL"
    writer.close()
  }
}