package com.ericsson.asyncprogramming.threadpool

import java.util.concurrent.ExecutorService
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import java.util.concurrent.Executors

object callerdemo extends App {
  val threadPool = Executors.newFixedThreadPool(3)
  try {
    val future = new FutureTask[String](new Callable[String] {
      override def call(): String = {
        Thread.sleep(100)
        "im result"
      }
    })
    threadPool.execute(future)
    println(future.get())
  } finally {
    threadPool.shutdown()
  }
}