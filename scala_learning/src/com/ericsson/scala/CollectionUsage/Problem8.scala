package com.ericsson.scala.CollectionUsage

object Problem8 extends App {
  val array: Array[Int] = Array(1, 2, 3, 4, 5, 6)
  array.grouped(2).foreach(x => println(x.mkString(" ")))
}