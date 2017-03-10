package com.ericsson.scala

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.Logging
import akka.actor.Props
import akka.actor.ActorSystem
import scala.io.Source
import scala.collection._

class ParentActor extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case "create" =>
      context.actorOf(Props[ChildActor])
      log.info(s"created a new child - children = ${context.children}")
    case "sayhi" =>
      log.info("Kids, say hi!")
      for (c <- context.children) c ! "sayhi"
    case "stop" =>
      log.info("parent stopping")
      context.stop(self)
  }
}

class ChildActor extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case "sayhi" =>
      val parent = context.parent
      log.info(s"my parent $parent made me say hi!")
  }
  override def postStop() {
    log.info("child stopped!")
  }
}

object ActorsHierarchy extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val parent = ourSystem.actorOf(Props[ParentActor], "parent")
  parent ! "create"
  parent ! "create"
  Thread.sleep(1000)
  parent ! "sayhi"
  Thread.sleep(1000)
  parent ! "stop"
  Thread.sleep(1000)
  ourSystem.shutdown()
}