package com.ericsson.cp

import java.util.concurrent._
import scala.concurrent._

object CollectionsIterators extends App{

  def execute(body: => Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })

  val queue = new LinkedBlockingQueue[String]
  for (i <- 1 to 5500) queue.offer(i.toString)
  execute {
    val it = queue.iterator
    while (it.hasNext) println(it.next())
  }
  for (i <- 1 to 5500) queue.poll()
}