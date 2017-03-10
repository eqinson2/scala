package com.ericsson.scala.Matches

import scala.io.Source
import java.io.File
import java.nio.file._

object FallThru extends App {
  val regex = "[\\s]*(case [^:]+:)[\\s]*(// Fall through|// just fall thru)".r
  var count: Int = _

  def countFallThru(path: String) = {
    if (path.endsWith(".java")) {
      Source.fromFile(path).getLines.toStream.foreach {
        _ match {
          case regex(x, y) => { count += 1; println(path, x, y, count) }
          case _           =>
        }
      }
    }
  }

  implicit def makeFileVisitor(f: (Path) => Unit) = new SimpleFileVisitor[Path] {
    override def visitFile(p: Path, attrs: attribute.BasicFileAttributes) = {
      f(p)
      FileVisitResult.CONTINUE
    }
  }

  Files.walkFileTree(new File("").toPath, (f: Path) => countFallThru(f.toString()))
}