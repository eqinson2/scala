package com.ericsson.cp

import scala.concurrent._

object CollectionsBad extends App {
  import scala.collection._

  val buffer = mutable.ArrayBuffer[Int]()

  def execute(body: => Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })

  def add(numbers: Seq[Int]) = execute {
    synchronized {
      buffer ++= numbers
      println(s"buffer = $buffer")
    }
  }

  add(0 until 10)
  add(10 until 20)

  Thread.sleep(5000)
}