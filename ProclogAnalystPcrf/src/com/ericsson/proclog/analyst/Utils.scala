package com.ericsson.proclog.analyst

import java.io.{File, IOException, UnsupportedEncodingException}
import java.nio.file.{FileAlreadyExistsException, Files, Path, Paths, StandardOpenOption}

object Utils {
  def getRecursively(f: File): Seq[File] = f.listFiles.filter(!_.isDirectory) ++ f.listFiles.filter(_.isDirectory).flatMap(getRecursively)

  def deleteOutputDir(directory: File) = {
    if (directory.exists) {
      getRecursively(directory).foreach { f =>
        f.delete() match {
          case true  => println(s"sub dir deleted: ${f.getAbsolutePath}");
          case false => throw new IOException("Failed to delete " + f.getAbsolutePath)
        }
      }
    }
  }

  def createOutputDirectory(directory: File) {
    if (!directory.exists) {
      directory.mkdir
      println(s"directory has been created: ${directory.getAbsolutePath}")
    }
  }

  def prepareFile(name: String) = {
    val file = Paths.get(LogAnalystMain.outputDirName, name + ".log");
    try {
      Files createFile file
    } catch {
      case e: FileAlreadyExistsException =>
      case e: IOException                => println(e.getMessage())
      case _: Throwable                  => println("Other exception!")
    }
    file
  }

  def write(cso: CSOEntry, file: Path) = {
    try {
      java.nio.file.Files.write(file, cso.toString.getBytes("utf-8"), StandardOpenOption.APPEND)
    } catch {
      case e: UnsupportedEncodingException => println(e.getMessage())
      case e: IOException                  => println(e.getMessage())
    }
  }

}