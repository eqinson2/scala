package com.ericsson.asyncprogramming.threadpool

import java.util.concurrent.Executors

class ThreadDemo(threadName: String) extends Runnable {
  override def run() {
    for (i <- 1 to 10) {
      println(threadName + "|" + i)
      Thread.sleep(100)
    }
  }
}

object runnabledemo extends App {
  val threadPool = Executors.newFixedThreadPool(5)
  try {
    for (i <- 1 to 5) {
      threadPool.execute(new ThreadDemo("thread" + i))
    }
  } finally {
    threadPool.shutdown()
  }
}