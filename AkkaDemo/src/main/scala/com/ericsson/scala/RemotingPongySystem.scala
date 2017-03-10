package com.ericsson.scala

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

import com.typesafe.config.ConfigFactory

import akka.actor.Actor
import akka.actor.ActorIdentity
import akka.actor.ActorRef
import akka.actor.ActorSelection.toScala
import akka.actor.ActorSystem
import akka.actor.Identify
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.event.Logging
import akka.pattern.ask
import akka.pattern.pipe
import akka.remote.RemotingLifecycleEvent
import akka.util.Timeout

class RemotePongy extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case "ping" =>
      log.info("Got a ping -- ponging back!")
      sender ! "pong"
  }
  override def postStop() = log.info("pongy going down")
}

object RemotingPongySystem extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")

  def remotingConfig(port: Int) = ConfigFactory.parseString(s"""
akka {
  actor.provider = "akka.remote.RemoteActorRefProvider"
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = $port
    }
  }
}
  """)

  def remotingSystem(name: String, port: Int) = ActorSystem(name, remotingConfig(port))

  val system = remotingSystem("PongyDimension", 24321)
  val pongy = system.actorOf(Props[RemotePongy], "RemotePongy")
  Thread.sleep(1000 * 60)
  system.shutdown()
}

class RemotePingy extends Actor {
  val log = Logging(context.system, this)
  def receive = {
    case pongyRef: ActorRef =>
      implicit val timeout = Timeout(2 seconds)
      val future = pongyRef ? "ping"
      pipe(future) to sender
  }
}

class PingyRunner extends Actor {
  val log = Logging(context.system, this)
  val pingy = context.actorOf(Props[RemotePingy], "RemotePingy")
  def receive = {
    case "start" =>
      val path = context.actorSelection("akka.tcp://PongyDimension@127.0.0.1:24321/user/RemotePongy")
      path ! Identify(0)
    case ActorIdentity(0, Some(ref)) =>
      for (i <- 0 until 10) {
        pingy ! ref
        Thread.sleep(1000)
      }
    case ActorIdentity(0, None) =>
      log.info("Something's wrong -- no pongy anywhere!")
      context.stop(self)
    case "pong" =>
      log.info("got a pong from another dimension.")
    //context.stop(self)
  }
}

object RemotingPingySystem extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")

  def remotingConfig(port: Int) = ConfigFactory.parseString(s"""
akka {
  actor.provider = "akka.remote.RemoteActorRefProvider"
  remote {
    enabled-transports = ["akka.remote.netty.tcp"]
    netty.tcp {
      hostname = "127.0.0.1"
      port = $port
    }
  }
}
  """)

  def remotingSystem(name: String, port: Int) = ActorSystem(name, remotingConfig(port))

  val system = remotingSystem("PingyDimension", 24567)
  val runner = system.actorOf(Props[PingyRunner], "runner")

  val remoteEventSnitch = system.actorOf(RemoteEventSnitch.props(), "RemoteEventSnitch")
  system.eventStream.subscribe(remoteEventSnitch, classOf[RemotingLifecycleEvent])

  runner ! "start"
  Thread.sleep(1000 * 60)
  system.shutdown()
}


