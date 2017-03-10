package com.ericsson.asyncprogramming.future

object FuturesReduce extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global

  val squares = for (i <- 1 to 10) yield Future {
    System.out.println(Thread.currentThread().getName());
    Thread.sleep(100);
    i * i
  }
  val sumOfSquares = Future.reduce(squares)(_ + _)
  System.out.println("reduce finised")

  sumOfSquares foreach {
    case sum => println(s"Sum of squares = $sum")
  }

  Thread.sleep(10 * 1000)
}

object FuturesReduce2 extends App {
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  import scala.concurrent.{ Await, Future }
  import scala.util.Random

  val futures: List[Future[List[String]]] =
    List.fill(10)(Future {
      System.out.println(Thread.currentThread().getName());
      List(scala.math.BigInt(Random.nextInt).toString())
    })

  val future: Future[List[String]] = Future.fold(futures)(List[String]())((acc, e) => acc ++ e)
  System.out.println("reduce finised")

  future foreach {
    text => println(text)
  }
  Await.result(future, 1 second)
  Thread.sleep(10 * 1000)

}