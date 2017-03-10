package com.ericsson.scala.Actors.demo

import scala.actors.Actor
import scala.actors.Actor._

class HiActor extends Actor {
  def act() {
    while (true) {
      receive {
        case "Hi" => println("Hello")
      }
    }
  }
}

object HiActor extends App {
  val actor1 = new HiActor
  actor1.start
  actor1 ! "Hi"

  val actor2 = actor {//匿名Actor
    while (true) {
      receive {
        case "Hi" => println("Hello")
      }
    }
  }
  actor2 ! "Hello"
  
  Thread.sleep(10000)
}