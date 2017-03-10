package com.ericsson.asyncprogramming.promise

object PromisesCreate extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global

  val p = Promise[String]
  val q = Promise[String]

  p.future foreach {
    case text => println(s"Promise p succeeded with '$text'")
  }

  p success "kept"

  val secondAttempt = p trySuccess "kept again"

  println(s"Second attempt to complete the same promise went well? $secondAttempt")

  q failure new Exception("not kept")

  q.future.failed foreach {
    case t => println(s"Promise q failed with $t")
  }

}