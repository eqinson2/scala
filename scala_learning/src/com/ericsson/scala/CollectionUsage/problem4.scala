package com.ericsson.scala.CollectionUsage

object problem4 extends App {
  def func(array: Array[String], map: Map[String, Int]) = {
    val set1 = array.toSet
    val set2 = map.keys.toSet
    val set3 = set1 & set2
    val predicate = (x: String) => set3.contains(x)
    val result = map.filter(kv => predicate(kv._1)).values.mkString(" ")
    println(result)
  }

  func(Array("Tom", "Fred", "Harry"), Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5))
}