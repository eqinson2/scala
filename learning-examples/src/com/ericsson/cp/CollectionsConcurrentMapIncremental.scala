package com.ericsson.cp

object CollectionsConcurrentMapIncremental extends App {
  import java.util.concurrent.ConcurrentHashMap
  import scala.collection._
  import scala.collection.convert.decorateAsScala._
  import scala.annotation.tailrec
  import scala.concurrent._

  val emails = new ConcurrentHashMap[String, List[String]]().asScala

  def execute(body: => Unit) = ExecutionContext.global.execute(new Runnable {
    def run() = body
  })

  @tailrec def addEmail(name: String, address: String) {
    emails.get(name) match {
      case Some(existing) =>
        if (!emails.replace(name, existing, address :: existing)) addEmail(name, address)
      case None =>
        if (emails.putIfAbsent(name, address :: Nil) != None) addEmail(name, address)
    }
  }

  execute {
    addEmail("Yukihiro Matsumoto", "ym@ruby.com")
    println(s"emails = $emails")
  }

  execute {
    addEmail("Yukihiro Matsumoto", "ym@ruby.io")
    println(s"emails = $emails")
  }

  Thread.sleep(5000)

}