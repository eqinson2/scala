package com.ericsson.scala

import scala.collection.mutable.ArrayBuffer

object ArrayDemo extends App {
  def genRandomArr(n: Int) = {
    val r = new scala.util.Random
    (1 to n).map { _ => r.nextInt(n) }
  }

  println(genRandomArr(10).mkString(" "))

  def swap(a: Array[Int]) = {
    for (index <- 0 until a.length - 1 if index % 2 == 0) {
      val y = a(index); a(index) = a(index + 1); a(index + 1) = y
    }
    a
  }

  println(swap((1 to 100).toArray).mkString(" "))

  val a = (100 to 200).toArray

  def swap2(a: Array[Int]) = (0 to a.length - 2).flatMap { x => Array(a(x + 1), a(x)) }.mkString(" ")

  println(swap2(a))

  val b = (-10 to 10).toArray

  def sort(a: Array[Int]) = {
    val buffer = new ArrayBuffer[Int](a.length)
    buffer.++=(a.filter(_ > 0).sortWith(_ > _)).++=(a.filter(_ <= 0).sortWith(_ > _)).mkString(" ")
  }

  println(sort(b))

  def avg(a: Array[Double]) = {
    (0.0D /: a) ((x, y) => x + y) / a.length
  }

  println(avg(Array(1.23, 3.45, 6, 66)))

  def removeDup(a: ArrayBuffer[Int]) = {
    val index = for (x <- 0 to a.length - 1; from = x + 1; y <- from to a.length - 1; if a(x) == a(y))
      yield x

    val index2 = (0 until a.length).filter {
      !index.contains(_)
    }
    for (i <- index2)
      yield a(i)
  }

  println(removeDup(ArrayBuffer(0, 1, 2, 3, 4, 4, 4, 5, 6, 7, 8, 8, 8, 8)).mkString(" "))

  def removeDup2(a: ArrayBuffer[Int]) = {
    var n = a.length - 1;
    var x = 0
    while (x < n) {
      val from = x + 1
      var skip = false
      var y = from
      while (y <= n && !skip) {
        if (a(x) == a(y)) {
          a.remove(x)
          n -= 1;
          skip = true
        }
        y += 1
      }
      if (!skip) x += 1
    }
    a.mkString(" ")
  }

  println(removeDup2(ArrayBuffer(0, 1, 2, 3, 4, 4, 4, 5, 6, 7, 8, 8, 8, 8)))

  java.util.TimeZone.getAvailableIDs filter (_.startsWith("America")) map (_.substring("America".length + 1)) sortWith (_ < _) foreach {
    println _
  }

}