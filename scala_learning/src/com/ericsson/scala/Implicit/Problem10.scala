package com.ericsson.scala.Implicit

object Problem10 extends App {
  println("abc".map { _.toUpper })
  println("abc".map { _.toInt })
}