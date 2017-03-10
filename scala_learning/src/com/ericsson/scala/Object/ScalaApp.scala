package com.ericsson.scala.Object

object ScalaApp extends App {
  args.reverse.map { s => print(s + " ") }
}