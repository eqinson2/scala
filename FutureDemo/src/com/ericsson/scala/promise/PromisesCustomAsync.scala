package com.ericsson.scala.promise

object PromisesCustomAsync extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.util.control.NonFatal

  def myFuture[T](body: => T): Future[T] = {
    val p = Promise[T]

    global.execute(new Runnable {
      def run() = try {
        val result = body
        p success result
      } catch {
        case NonFatal(e) =>
          p failure e
      }
    })

    p.future
  }

  val future = myFuture {
    "naaa" + "na" * 8 + " Katamari Damacy!"
  }

  future foreach {
    case text => println(text)
  }
  
  Thread.sleep(10 * 1000)

}