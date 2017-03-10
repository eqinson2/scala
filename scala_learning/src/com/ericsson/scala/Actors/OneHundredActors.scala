package com.ericsson.scala.Actors

import scala.actors._
import Actor._

class Actor1 extends Actor {
  override def act() {
    while (true) {
      receive {
        case msg: String => println("task1: " + Thread.currentThread)
        case _           =>
      }
    }
  }
}
class Actor2 extends Actor {
  override def act() {
    react {
      case msg: String => { println(msg + "			handled by task2: " + Thread.currentThread); act(); }
      case _           =>
    }
    println("after call react, never reached.")
  }
}

object OneHundredActors extends App {
    val actor1Array = new Array[Actor](100)
    for (i <- 0 until 100) {
      val task = new Actor1
      actor1Array(i) = task
      task.start
    }

//    val actor2Array = new Array[Actor](100)
//    for (i <- 0 until 100) {
//      val task = new Actor2
//      actor2Array(i) = task
//      task.start
//    }

  val task = new Actor2
  task.start

  for (i <- 0 until 100) {
    actor1Array(i) ! "Hello"
    //task ! "Hello"
  }
}