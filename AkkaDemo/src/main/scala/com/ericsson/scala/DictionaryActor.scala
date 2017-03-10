package com.ericsson.scala

import scala.collection.mutable
import scala.io.Source

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.event.Logging

class DictionaryActor extends Actor {
  private val log = Logging(context.system, this)
  private val dictionary = mutable.Set[String]()
  def receive = uninitialized
  def uninitialized: PartialFunction[Any, Unit] = {
    case DictionaryActor.Init(path) =>
      val stream = getClass.getResourceAsStream(path)
      val words = Source.fromInputStream(stream)
      for (w <- words.getLines) dictionary += w
      context.become(initialized)
  }
  def initialized: PartialFunction[Any, Unit] = {
    case DictionaryActor.IsWord(w) =>
      log.info(s"word '$w' exists: ${dictionary(w)}")
    case DictionaryActor.End =>
      dictionary.clear()
      context.become(uninitialized)
  }
  override def unhandled(msg: Any) = {
    log.info(s"message $msg should not be sent in this state.")
  }
}

object DictionaryActor {
  case class Init(path: String)
  case class IsWord(w: String)
  case object End
}

object ActorsBecome extends App {
  lazy val ourSystem = ActorSystem("OurExampleSystem")
  val dict = ourSystem.actorOf(Props[DictionaryActor], "dictionary")
  dict ! DictionaryActor.IsWord("program")
  Thread.sleep(1000)
  dict ! DictionaryActor.Init("/org/learningconcurrency/words.txt") // or /usr/share/dict/words 
  Thread.sleep(1000)
  dict ! DictionaryActor.IsWord("program")
  Thread.sleep(1000)
  dict ! DictionaryActor.IsWord("balaban")
  Thread.sleep(1000)
  dict ! DictionaryActor.End
  Thread.sleep(1000)
  dict ! DictionaryActor.IsWord("termination")
  Thread.sleep(1000)
  ourSystem.shutdown()
}
