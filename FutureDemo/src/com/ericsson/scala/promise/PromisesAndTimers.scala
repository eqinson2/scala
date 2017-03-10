package com.ericsson.scala.promise

object PromisesAndTimers extends App {
  import java.util._
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import PromisesAndCustomOperations._

  private val timer = new Timer(true)

  def timeout(millis: Long): Future[Unit] = {
    val p = Promise[Unit]
    timer.schedule(new TimerTask {
      def run() = p success ()
    }, millis)
    p.future
  }

  val f = timeout(1000).map(_ => "timeout!") or Future {
    Thread.sleep(999)
    "work completed!"
  }

  f foreach {
    case text => println(text)
  }

  Thread.sleep(10 * 1000)
}