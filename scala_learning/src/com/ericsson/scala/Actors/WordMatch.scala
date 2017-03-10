package com.ericsson.scala.Actors

import scala.io.Source
import scala.actors._
import scala.actors.Actor._

import java.io.File
import java.nio.file._
import java.io.IOException

class ReceiverActor() extends Actor {
  override def act() {
    var resultMap = Map[String, List[String]]()
    while (true) {
      receive {
        case (reply: String, filename: String) =>
          resultMap = resultMap + (reply -> (resultMap.getOrElse(reply, List[String]()) :+ filename))
        case _ =>
      }
    }
  }
}

class MatcherActor(fn: String, from: Actor) extends Actor {
  val regex = "[\\s]*case [^:]+:[\\s]*// Fall through".r
  override def act {
    if (!fn.endsWith(".java"))
      throw new IOException("Illegal file name " + fn)

    for (line <- Source.fromFile(fn).getLines())
      regex.findAllIn(line).foreach { from ! (_, fn) }
  }

  override def exceptionHandler = {
    case e: java.nio.charset.MalformedInputException => println(e.getMessage())
  }
}

object WordMatch extends App {
  implicit def makeFileVisitor(f: (Path) => Unit) = new SimpleFileVisitor[Path] {
    override def visitFile(p: Path, attrs: attribute.BasicFileAttributes) = {
      f(p)
      FileVisitResult.CONTINUE
    }
  }

  val receiver = new ReceiverActor
  receiver.start

  val monitor = new ExceptionMonitor
  monitor.start

  actor {
    val f: (String) => Unit = (fn: String) => {
      val task = new MatcherActor(fn, receiver)
      task.start
    }
    Files.walkFileTree(new File("c://src").toPath, (path: Path) => f(path.toString()))
  }
}
