package com.ericsson.scala

import java.io.File
import java.io.PrintWriter

object WriteToFile {
  def writeToFile(filename: String)(codeBlock: PrintWriter => Unit) =
    {
      val writer = new PrintWriter(new File(filename))
      try { codeBlock(writer) } finally { writer.close() }
    }

  def main(args: Array[String]): Unit = {
    writeToFile("output.txt") { _ write "hello from scala" }
  }
}