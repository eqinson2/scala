package com.ericsson.scala.FileAndRegex

import java.io.File
import java.nio.file._

object filewalk extends App {
  println("==================================test1=================================")

  def subDirs(dir: File): Iterator[File] = {
    val children = dir.listFiles.filter {
      _.isDirectory
    }
    children.toIterator ++ children.toIterator.flatMap(
      subDirs _
    )
  }

  for (file <- subDirs(new File("C://maworkspace//scala")))
    println(file.toString)

  println("==================================test2=================================")

  implicit def makeFileVisitor(f: (Path) => Unit): FileVisitor[Path] = new SimpleFileVisitor[Path] {
    override def visitFile(p: Path, attrs: attribute.BasicFileAttributes) = {
      f(p)
      FileVisitResult.CONTINUE
    }

    override def preVisitDirectory(p: Path, attrs: attribute.BasicFileAttributes) = {
      f(p)
      FileVisitResult.CONTINUE
    }
  }

  Files.walkFileTree(new File("C://maworkspace//scala").toPath, (f: Path) => println(f))
}