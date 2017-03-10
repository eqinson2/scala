package com.ericsson.scala.future

object FuturesReduce extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global

  val squares = for (i <- 0 until 10) yield Future { i * i }
  val sumOfSquares = Future.reduce(squares)(_ + _)

  sumOfSquares foreach {
    case sum => println(s"Sum of squares = $sum")
  }
  
  Thread.sleep(10 * 1000)
}