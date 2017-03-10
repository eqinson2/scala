package com.ericsson.scala

import akka.actor._
import akka.event.Logging
import akka.util.Timeout
import akka.pattern.{ ask, pipe, gracefulStop }
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

class Pongy extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case "ping" =>
      log.info("Got a ping -- ponging back!")
      sender ! "pong"
      context.stop(self)
  }
  override def postStop() = log.info("pongy going down")
}

class Pingy extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case pongyRef: ActorRef =>
      implicit val timeout = Timeout(2 seconds)
      val future = pongyRef ? "ping"
//            future foreach {
//              value => log.info(value.asInstanceOf[String])
//            }
//            future onComplete {
//              value => log.info("Pingy got " + value.get.asInstanceOf[String])
//            }
      pipe(future) to sender
  }
}

class Master extends Actor {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val log = Logging(context.system, this)
  val pingy = ourSystem.actorOf(Props[Pingy], "pingy")
  val pongy = ourSystem.actorOf(Props[Pongy], "pongy")
  def receive = {
    case "start" =>
      pingy ! pongy
    case "pong" =>
      log.info("got a pong back!")
      context.stop(self)
  }
  override def postStop() = log.info("master going down")
}

object CommunicatingAsk extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val masta = ourSystem.actorOf(Props[Master], "masta")
  masta ! "start"
  Thread.sleep(1000)
  ourSystem.shutdown()
}