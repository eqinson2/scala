package com.ericsson.scala

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Success

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Terminated
import akka.actor.actorRef2Scala
import akka.pattern.gracefulStop

class GracefulPingy extends Actor {
  val pongy = context.actorOf(Props[Pongy], "pongy")
  context.watch(pongy)

  def receive = {
    case GracefulPingy.CustomShutdown =>
      context.stop(pongy)
    case Terminated(`pongy`) =>
      context.stop(self)
  }
}

object GracefulPingy {
  object CustomShutdown
}

object CommunicatingGracefulStop extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val grace = ourSystem.actorOf(Props[GracefulPingy], "grace")
  val stopped = gracefulStop(grace, 3.seconds, GracefulPingy.CustomShutdown)
  stopped onComplete {
    case Success(x) =>
      println("graceful shutdown successful")
      ourSystem.shutdown()
    case Failure(t) =>
      println("grace not stopped!")
      ourSystem.shutdown()
  }
}