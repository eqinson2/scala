package com.ericsson.scala

object FoldLeftTest extends App {
  val list1 = List(1, 2, 3)
  val list2 = List(1, 2, 3)
  val list3 = List(1, 2, 3, 4, 5)
  println((0 /: list1)((b, a) => b + a))
  println((1 /: list1)((b, a) => b * a))
  println((List[Int]() /: list3)((b, a) => a :: b))
}