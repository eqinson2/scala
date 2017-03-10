package com.ericsson.scala

import akka.actor._
import akka.event.Logging
import akka.pattern.pipe
import akka.actor.SupervisorStrategy._
import scala.io.Source
import scala.collection._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class Naughty extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case s: String => log.info(s)
    case msg       => throw new RuntimeException
  }
  override def postRestart(t: Throwable) = log.info("naughty restarted")
}

class Supervisor extends Actor {
  val child = context.actorOf(Props[Naughty], "victim")
  def receive = PartialFunction.empty
  override val supervisorStrategy =
    OneForOneStrategy() {
      case ake: ActorKilledException => Restart
      case _                         => Escalate
    }
}

object SupervisionKill extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val s = ourSystem.actorOf(Props[Supervisor], "super")
  ourSystem.actorSelection("/user/super/*") ! Kill
  ourSystem.actorSelection("/user/super/*") ! "sorry about that"
  ourSystem.actorSelection("/user/super/*") ! "kaboom".toList
  Thread.sleep(1000)
  ourSystem.stop(s)
  Thread.sleep(1000)
  ourSystem.shutdown()
}