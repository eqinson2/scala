package com.ericsson.scala.promise

import scala.concurrent._
import ExecutionContext.Implicits.global

object PromisesAndCustomOperations extends App {
  implicit class FutureOps[T](val self: Future[T]) {
    def or(that: Future[T]): Future[T] = {
      val p = Promise[T]
      self onComplete { case x => p tryComplete x }
      that onComplete { case y => p tryComplete y }
      p.future
    }
  }

  val f = Future { "now" } or Future { "later" }

  f foreach {
    case when => println(s"The future is $when")
  }

  Thread.sleep(10 * 1000)

}
