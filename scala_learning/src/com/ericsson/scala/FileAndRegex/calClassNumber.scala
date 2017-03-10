package com.ericsson.scala.FileAndRegex

import java.nio.file._
import java.io.File

object calClassNumber extends App {
  var i: Int = _

  implicit def makeFileVisitor(f: (Path) => Unit): FileVisitor[Path] = new SimpleFileVisitor[Path] {
    override def visitFile(p: Path, attrs: attribute.BasicFileAttributes) = {
      f(p)
      FileVisitResult.CONTINUE
    }
  }

  Files.walkFileTree(new File("C://maworkspace//scala").toPath, (p: Path) => if (p.getFileName.toString.endsWith(".class")) i += 1)

  print(i)
}
