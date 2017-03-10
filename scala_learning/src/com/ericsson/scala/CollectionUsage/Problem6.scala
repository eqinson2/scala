package com.ericsson.scala.CollectionUsage

object Problem6 extends App {
  val lst = "i am a engineer".split(" ").toList
  val result1 = (lst :\ List[String]())(_ :: _)
  println(result1)

  val result2 = (List[String]() /: lst)((a, b) => b :: a)
  println(result2)
}