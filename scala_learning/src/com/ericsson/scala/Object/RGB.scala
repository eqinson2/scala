package com.ericsson.scala.Object

object RGB extends Enumeration with App {
  val RED = Value(0xff0000, "Red")
  val BLACK = Value(0x000000, "Black")
  val GREEN = Value(0x00ff00, "Green")
  val YELLOW = Value(0xffff00, "Yellow")
  val WHITE = Value(0xffffff, "White")
}