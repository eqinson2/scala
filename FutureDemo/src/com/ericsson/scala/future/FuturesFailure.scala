package com.ericsson.scala.future

object FuturesFailure extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source

  val urlSpec: Future[String] = Future {
    Source.fromURL("http://www.w3.org/non-existent-url-spec.txt").mkString
  }

  urlSpec.failed foreach {
    case t => println(s"exception occurred - $t")
  }
  
  Thread.sleep(10 * 1000)
}