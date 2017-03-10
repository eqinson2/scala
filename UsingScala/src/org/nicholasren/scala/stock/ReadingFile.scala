package org.nicholasren.scala.stock
import scala.io.Source

object ReadingFile {
  def main(args: Array[String]) {
    println("*** The Content of the file you read is: ")

    //TODO
    Source.fromFile("/media/Development/workspace/ScalaGettingStarted/ReadingFile.scala").foreach { print }
  }
}