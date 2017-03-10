package com.ericsson.scala.Actors

import scala.io.Source
import scala.actors._
import scala.actors.Actor._

import java.io.File
import java.nio.file._
import java.io.IOException
import java.nio.charset.MalformedInputException

class ReceiverActor2() extends Actor {
  override def act() {
    var resultMap = Map[String, List[String]]()
    while (true) {
      receive {
        case (reply: String, filename: String) =>
          resultMap = resultMap + (reply -> (resultMap.getOrElse(reply, List[String]()) :+ filename))
          for ((k, v) <- resultMap) println(v, k)
        case _ =>
      }
    }
  }
}

class MatcherActor2(fn: String, from: Actor) extends Actor {
  val regex = "[\\s]*case [^:]+:[\\s]*// Fall through".r

  override def act {
    if (!fn.endsWith(".java"))
      throw new IOException("Illegal file name: " + fn)

    for (line <- Source.fromFile(fn).getLines())
      regex.findAllIn(line).foreach { from ! (_, fn) }

  }
}

class ExceptionMonitor extends Actor {
  var slaves = Set[Actor]()

  def linkedWith(slave: Actor) = {
    slaves = slaves + slave
  }
  override def act {
    for (s <- slaves) link(s)

    while (true) {
      receive {
        case e: MalformedInputException => println("handle MalformedInputException: " + e.getMessage())
        case e: IOException             => println("handled IOException: " + e.getMessage)
        case e: RuntimeException        => println("handled RuntimeException: " + e.getMessage)
        case _                          =>
      }
    }
  }
}

object WordMatcher extends App {
  val receiver = new ReceiverActor2
  receiver.start

  val monitor = new ExceptionMonitor
  monitor.start

  def getAllFiles(directory: File): Seq[File] = {
    val dirChildren: Seq[File] = directory.listFiles.filter(_.isDirectory)
    val fileChildren: Seq[File] = directory.listFiles.filter(!_.isDirectory)
    fileChildren ++ dirChildren.flatMap(getAllFiles _)
  }

  actor {
    val f = (fn: String) => {
      val task = new MatcherActor2(fn, receiver)
      monitor.linkedWith(task)
      task.start
    }
    for (c <- getAllFiles(new File("c://src")))
      f(c.getPath)
  }
}