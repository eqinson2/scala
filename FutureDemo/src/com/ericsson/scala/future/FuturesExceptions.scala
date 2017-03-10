package com.ericsson.scala.future

object FuturesExceptions extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source

  val file = Future { Source.fromFile("file").getLines.mkString("\n") }

  file foreach {
    text => println(text)
  }

  file.failed foreach {
    case fnfe: java.io.FileNotFoundException => println(s"Cannot find file - $fnfe")
    case t                                   => println(s"Failed due to $t")
  }

  import scala.util.{ Try, Success, Failure }

  file onComplete {
    case Success(text) => println(text)
    case Failure(t)    => println(s"Failed due to $t")
  }
  
  Thread.sleep(10 * 1000)

}