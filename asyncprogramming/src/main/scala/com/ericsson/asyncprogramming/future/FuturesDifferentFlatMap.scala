package com.ericsson.asyncprogramming.future

object FuturesDifferentFlatMap extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source

  val answer = for {
    nettext <- Future { println("11111"); Source.fromURL("http://www.ietf.org/rfc/rfc1855.txt").mkString }
    urltext <- Future { println("22222"); Source.fromURL("http://www.w3.org/Addressing/URL/url-spec.txt").mkString }
  } yield {
    "First of all, read this: " + nettext + " Once you're done, try this: " + urltext
  }

  answer foreach {
    case contents => println(contents)
  }

  Thread.sleep(10 * 1000)
}