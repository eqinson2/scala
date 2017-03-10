package com.ericsson.scala.HighOrderFunc

import scala.annotation.tailrec

object HighOrderFunc extends App {
  def values(func: (Int) => Int, low: Int, high: Int) = {
    for (i <- low to high)
      print(i, func(i))
  }

  values(x => x * x, -5, 5)
  println

  val array = Array(1, 2, 3, 4, 5)
  val max = array.reduceLeft(Math.max(_, _))
  println("max:" + max)

  def factorial1(value: Int): Int = {
    if (value == 0 || value == 1) 1
    else value * factorial1(value - 1)
  }

  println(factorial1(8))

  def factorial2(value: Int) = {
    if (value < 1) 0
    else
      (1 to value).toList.reduceLeft(_ * _)
  }

  println(factorial2(8))

  def factorial3(value: Int) = {
    if (value < 1) 0
    else
      (1 /: (1 to value).toList) ((a, b) => (a * b))
  }

  println(factorial2(8))

  def largest(func: (Int) => Int, inputs: Seq[Int]) = {
    (Integer.MIN_VALUE /: (inputs.map {
      func(_)
    })) ((a, b) => Math.max(a, b))
  }

  println(largest(x => 10 * x - x * x, 1 to 10))

  def largestAt(func: (Int) => Int, inputs: Seq[Int]) = {
    inputs.map {
      func(_)
    }.zipWithIndex.sortWith(_._1 > _._1).head._2 + 1
  }

  println(largestAt(x => 10 * x - x * x, 1 to 10))

  val adjustPair = (f: (Int, Int) => Int, p: (Int, Int)) => f(p._1, p._2)
  val result = ((1 to 10) zip (11 to 20)).map(adjustPair(_ * _, _))
  println(result)

  val a = Array("Hello", "World")
  val b = Array("eqins", "eqins", "eqins")
  println(a.corresponds(b)((A: String, B: String) => A.length == B.length))

  def unless(condition: => Boolean)(block: => Unit) {
    if (!condition) block
  }

  var x = 0
  unless(x == 10) {
    println("x is not equal 0")
  }


  @tailrec
  def until(condition: => Boolean)(block: => Unit): Unit = {
    if (!condition) {
      block
      until(condition)(block)
    }
  }

  def indexOf(str: String, ch: Char): Int = {
    var i = 0
    until(i == str.length) {
      if (str(i) == ch) {
        println("ssss")
        return i
      }
      i += 1
    }
    println("found index.")
    return -1
  }

  indexOf("eqinson", 'n')

}