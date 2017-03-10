package com.ericsson.scala

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.Logging
import akka.actor.Props
import akka.actor.ActorSystem
import scala.io.Source
import scala.collection._

class CheckActor extends Actor {
  import akka.actor.{Identify, ActorIdentity}
  val log = Logging(context.system, this)
  def receive = {
    case path: String =>
      log.info(s"checking path $path")
      context.actorSelection(path) ! Identify(path)
    case ActorIdentity(path, Some(ref)) =>
      log.info(s"found actor $ref on $path")
    case ActorIdentity(path, None) =>
      log.info(s"could not find an actor on $path")
  }
}


object ActorsIdentify extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val checker = ourSystem.actorOf(Props[CheckActor], "checker")
  checker ! "../*"
  Thread.sleep(1000)
  checker ! "../../*"
  Thread.sleep(1000)
  checker ! "/system/*"
  Thread.sleep(1000)
  checker ! "/user/checker2"
  Thread.sleep(1000)
  checker ! "akka://OurExampleSystem/system"
  Thread.sleep(1000)
  ourSystem.stop(checker)
  Thread.sleep(1000)
  ourSystem.shutdown()
}