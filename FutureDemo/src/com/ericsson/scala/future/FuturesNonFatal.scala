package com.ericsson.scala.future

import scala.util.control.NonFatal

object FuturesNonFatal extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global

  val f = Future { throw new InterruptedException }
  val g = Future { throw new IllegalArgumentException }
  f.failed foreach { case t => println(s"f error - $t") }
  g.failed foreach { case t => println(s"g error - $t") }
  f.failed foreach { case NonFatal(t) => println(s"$t is non-fatal from f!") }
  g.failed foreach { case NonFatal(t) => println(s"$t is non-fatal from g!") }

  Thread.sleep(1000 * 3)
}