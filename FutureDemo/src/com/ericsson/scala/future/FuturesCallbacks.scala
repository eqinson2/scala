package com.ericsson.scala.future

object FuturesCallbacks extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.io.Source

  def getUrlSpec(): Future[Seq[String]] = Future {
    val f = Source.fromURL("http://www.w3.org/Addressing/URL/url-spec.txt")
    Thread.sleep(3 * 1000)
    try f.getLines.toList finally f.close()
  }

  val urlSpec: Future[Seq[String]] = getUrlSpec()

  def find(lines: Seq[String], word: String) = lines.zipWithIndex collect {
    case (line, n) if line.contains(word) => (n, line)
  } mkString ("\n")

  urlSpec foreach {
    lines => println(s"Found occurrences of 'telnet'\n${find(lines, "telnet")}\n")
  }

  println("1111111111111")

  urlSpec foreach {
    lines => println(s"Found occurrences of 'password'\n${find(lines, "password")}\n")
  }
  println("222222222222222")

  println("callbacks installed, continuing with other work")

  Thread.sleep(10 * 1000)

}