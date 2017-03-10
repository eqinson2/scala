package com.ericsson.scala.Generic

class Pair[T, S](val first: T, val second: S) {
  def swap = new Pair(second, first)
  override def toString = { "Pair:" + first.toString + " " + second.toString }
}

class Pair2[T](var first: T, var second: T) {
  def swap = { val tmp = first; first = second; second = tmp }
  override def toString = { "Pair:" + first.toString + " " + second.toString }
}

class Pair3[T, S](val first: T, val second: S) {
  def swap(implicit ev: S =:= T, ev2: T =:= S) = new Pair(second, first)
  override def toString = { "Pair:" + first.toString + " " + second.toString }
}

object PairTest extends App {
  val p = new Pair(3, 4)
  println(p.swap)

  val p2 = new Pair(List(1, 2), List(3, 4))
  println(p2.swap)

  val pp = new Pair(3, 4)
  println(p.swap)

  val pp2 = new Pair(List(1, 2), List(3, 4))
  println(p2.swap)

  def swapPair[T, S](tuple: (T, S)): (S, T) = {
    (tuple._2, tuple._1)
  }

  println(swapPair((3, 4)))

  println(swapPair(List(1, 2), List(3, 4)))

  val ppp = new Pair3(3, 4)
  println(ppp.swap)

  val ppp2 = new Pair3(List(1, 2), List(3, 4))
  println(ppp2.swap)

  val ppp3 = new Pair3(Array(1, 2), List(3, 4))
  //println(ppp3.swap)

}