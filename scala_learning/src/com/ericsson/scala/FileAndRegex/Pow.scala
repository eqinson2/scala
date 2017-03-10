package com.ericsson.scala.FileAndRegex

import java.io.PrintWriter

object Pow extends App {
  val out = new PrintWriter("number.txt")
  for (i <- 0 to 20) {
    val left = BigInt(2).pow(i)
    val right = 1.0d / left.toDouble
    out.println((left, right))
  }
  out.close
}