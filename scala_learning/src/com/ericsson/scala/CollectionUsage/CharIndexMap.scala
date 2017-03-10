package com.ericsson.scala.CollectionUsage

import scala.collection.mutable.ArrayBuffer

object CharIndexMap extends App {
  def indexesMutable(s: String) = {
    val indexes = scala.collection.mutable.LinkedHashMap[Char, ArrayBuffer[Int]]()
    for (c <- s.zipWithIndex) {
      var list = indexes.getOrElse(c._1, ArrayBuffer[Int]())
      list += c._2
      indexes(c._1) = list
    }
    for ((k, v) <- indexes)
      println(k, v.mkString(" "))
  }

  indexesMutable("Mississippi Mississippi")

  def indexesInmutable(s: String) = {
    var indexes = Map[Char, List[Int]]()
    for (c <- s.zipWithIndex) {
      val oldList = indexes.getOrElse(c._1, List[Int]())
      indexes = indexes + (c._1 -> (oldList :+ c._2))
    }
    for ((k, v) <- indexes)
      println(k, v.mkString(" "))
  }

  println
  indexesInmutable("Mississippi Mississippi")

  println
  List(1, 2, 3, 4, 0, 0, 3).filter(_ != 0).foreach(print)
}