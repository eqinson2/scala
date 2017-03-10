package com.ericsson.scala

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.Logging
import akka.actor.Props
import akka.actor.ActorSystem
import scala.io.Source
import scala.collection._

class DeafActor extends Actor {
  val log = Logging(context.system, this)
  
  def receive = PartialFunction.empty
  
  override def unhandled(msg: Any) = msg match {
    case msg: String => log.info(s"could not handle '$msg'")
    case _           => super.unhandled(msg)
  }
}

object ActorsUnhandled extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val deafActor = ourSystem.actorOf(Props[DeafActor], name = "deafy")
  deafActor ! "hi"
  Thread.sleep(1000)
  deafActor ! 1234
  Thread.sleep(1000)
  ourSystem.shutdown()
}