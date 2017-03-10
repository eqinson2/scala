package com.ericsson.asyncprogramming.future

object FuturesLoopQuery extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source

  val buildFile: Future[String] = Future.apply {
    val f = Source.fromFile("file")
    try f.getLines.mkString("\n") finally f.close()
  }

  println(s"started reading build file asynchronously")
  println(s"status: ${buildFile.isCompleted}")
  Thread.sleep(250)
  println(s"status: ${buildFile.isCompleted}")
  println(s"status: ${buildFile.value}")

}