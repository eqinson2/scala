package com.ericsson.scala.CollectionUsage

import scala.io.Source

object stream extends App {
  def numFrom(n: BigInt): Stream[BigInt] = n #:: numFrom(n + 1)

  val tenOrMore = numFrom(10)
  println(tenOrMore)
  println(tenOrMore.tail)
  println(tenOrMore.tail.tail)

  val squares = numFrom(1).map(x => x * x)
  println(squares.take(5).force)
  //println(squares.force)

  val words = Source.fromFile("word_statistic.txt").getLines.toStream
  println(words)
  println(words(2))
  println(words)
}