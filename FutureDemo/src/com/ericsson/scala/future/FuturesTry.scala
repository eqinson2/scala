package com.ericsson.scala.future

object FuturesTry extends App {
  import scala.util._

  val threadName: Try[String] = Try(Thread.currentThread.getName)
  val someText: Try[String] = Try("Try objects are created synchronously")
  val message: Try[String] = for {
    tn <- threadName
    st <- someText
  } yield s"$st, t = $tn"

  message match {
    case Success(msg)   => println(msg)
    case Failure(error) => println(s"There should be no $error here.")
  }

}