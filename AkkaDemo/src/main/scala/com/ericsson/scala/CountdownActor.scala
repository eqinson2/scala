package com.ericsson.scala

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.Logging
import akka.actor.Props
import akka.actor.ActorSystem
import scala.io.Source
import scala.collection._

class CountdownActor extends Actor {
  var n = 10
  def counting: Actor.Receive = {
    case "count" =>
      n -= 1
      println(s"n = $n")
      if (n == 0) context.become(done)
  }
  def done = PartialFunction.empty
  def receive = counting
}


object ActorsCountdown extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val countdown = ourSystem.actorOf(Props[CountdownActor])
  for (i <- 0 until 20) countdown ! "count"
  Thread.sleep(10000)
  ourSystem.shutdown()
}