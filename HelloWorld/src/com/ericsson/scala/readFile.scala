package com.ericsson.scala
import scala.io.Source

object readFile {
  def printArgs(args: Array[String]): Unit = {
    args.foreach(println)
  }

  def main(args: Array[String]): Unit = {
    printArgs(args)
    if (args.length > 0) {
      for (line <- Source.fromFile(args(0)).getLines())
        println(line.length + " " + line)
    } else
      Console.err.println("Usage:Please enter filename")
  }
}