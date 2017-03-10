package com.ericsson.scala

import scala.actors._
import Actor._

object AskFortune extends App {
  val fortuneTeller = actor {
    for (i <- 1 to 4) {
      Thread.sleep(1000)
      receive {
        case _ => sender ! "Your day will rock! " + i
      }
    }
  }

  println(fortuneTeller !? (2000, "what is ahead?"))
  println(fortuneTeller !? (500, "what is ahead?"))

  val aPrinter = actor {
    receive { case msg => println("Ah, fortune message for you-" + msg) }
  }

  fortuneTeller.send("What is up?", aPrinter)
  fortuneTeller ! "How's my fortune?"

  Thread.sleep(3000)

  receive { case msg: String => println("Received " + msg) }
  println("Let's get the lost message")
  receive { case !(channel, msg) => println("Received belated message " + msg) }
}