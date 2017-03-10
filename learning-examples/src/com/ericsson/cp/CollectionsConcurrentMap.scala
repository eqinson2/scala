package com.ericsson.cp

import scala.concurrent._

object CollectionsConcurrentMap extends App {
  import java.util.concurrent.ConcurrentHashMap
  import scala.collection._
  import scala.collection.convert.decorateAsScala._
  import scala.annotation.tailrec

  val emails = new ConcurrentHashMap[String, List[String]]().asScala

  def execute(body: => Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })

  execute {
    emails("James Gosling") = List("james@javalove.com")
    println(s"emails = $emails")
  }

  execute {
    emails.putIfAbsent("Alexey Pajitnov", List("alexey@tetris.com"))
    println(s"emails = $emails")
  }

  execute {
    emails.putIfAbsent("Alexey Pajitnov", List("alexey@welltris.com"))
    println(s"emails = $emails")
  }

  Thread.sleep(5000)
}