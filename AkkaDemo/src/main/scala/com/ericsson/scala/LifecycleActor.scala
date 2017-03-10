package com.ericsson.scala

import akka.actor.Actor
import akka.actor.ActorRef
import akka.event.Logging
import akka.actor.Props
import akka.actor.ActorSystem
import scala.io.Source
import scala.collection._

class LifecycleActor extends Actor {
  val log = Logging(context.system, this)
  var child: ActorRef = _

  def receive = {
    case num: Double  => log.info(s"got a double - $num")
    case num: Int     => log.info(s"got an integer - $num")
    case lst: List[_] => log.info(s"list - ${lst.head}, ...")
    case txt: String  => child ! txt
  }

  override def preStart(): Unit = {
    log.info("about to start")
    child = context.actorOf(Props[StringPrinter], "kiddo")
  }

  override def preRestart(reason: Throwable, msg: Option[Any]): Unit = {
    log.info(s"about to restart because of $reason, during message $msg")
    super.preRestart(reason, msg)
  }

  override def postRestart(reason: Throwable): Unit = {
    log.info(s"just restarted due to $reason")
    super.postRestart(reason)
  }

  override def postStop() = log.info("just stopped")
}

class StringPrinter extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case msg => log.info(s"child got message '$msg'")
  }
  override def preStart(): Unit = log.info(s"child about to start.")
  override def postStop(): Unit = log.info(s"child just stopped.")
}

object ActorsLifecycle extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val testy = ourSystem.actorOf(Props[LifecycleActor], "testy")
  testy ! math.Pi
  Thread.sleep(1000)
  testy ! 7
  Thread.sleep(1000)
  testy ! "hi there!"
  Thread.sleep(1000)
  testy ! Nil
  Thread.sleep(1000)
  testy ! "sorry about that"
  Thread.sleep(1000)
  ourSystem.stop(testy)
  Thread.sleep(1000)
  ourSystem.shutdown()
}