package com.ericsson.cp

import scala.collection.concurrent
import scala.collection.convert.decorateAsScala

object CollectionsTrieMapBulk extends App {
  import scala.concurrent._

  val names = new concurrent.TrieMap[String, Int]
  names("Janice") = 0
  names("Jackie") = 0
  names("Jill") = 0

  def execute(body: => Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })

  execute {
    for (n <- 10 until 100) names(s"John $n") = n
  }

  Thread.sleep(5000)

  execute {
    println("snapshot time!")
    for (n <- names.map(_._1).toSeq.sorted) println(s"name: $n")
  }

  Thread.sleep(5000)

}