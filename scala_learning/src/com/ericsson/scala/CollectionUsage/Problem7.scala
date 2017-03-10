package com.ericsson.scala.CollectionUsage

object Problem7 extends App {
  val prices = List(5.0, 20.0, 9.95)
  val quanties = List(10, 2, 1)

  def tupled(tuple: (Double, Int)) = {
    tuple._1 * tuple._2
  }

  println(prices.zip(quanties).map(tupled))
}