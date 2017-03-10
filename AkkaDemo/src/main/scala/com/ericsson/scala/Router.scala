package com.ericsson.scala

import akka.actor._
import akka.event.Logging
import akka.util.Timeout
import akka.pattern.{ ask, pipe, gracefulStop }
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

class Router extends Actor {
  var i = 0
  val children = for (_ <- 0 until 4) yield context.actorOf(Props[StringPrinter])
  def receive = {
    case "stop" => context.stop(self)
    case msg =>
      children(i) forward msg
      i = (i + 1) % 4
  }
}


object CommunicatingRouter extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val router = ourSystem.actorOf(Props[Router], "router")
  router ! "Hi."
  router ! "I'm talking to you!"
  Thread.sleep(1000)
  router ! "stop"
  Thread.sleep(1000)
  ourSystem.shutdown()
}