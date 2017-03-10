package com.ericsson.proclog.analyst

import java.io.File

import scala.io.Source

object LogAnalystMain extends App {
  val time = System.currentTimeMillis
  val dirName = "pcrf"
  val outputDirName = "out_" + dirName
  val inputdir = new File(dirName)
  val outdir = new File(outputDirName)
  Utils deleteOutputDir outdir
  Utils createOutputDirectory outdir

  val process = (file: File) => AnalysisWork(Source.fromFile(file.toPath.toString).getLines().mkString("\n")).analyze()
  Utils.getRecursively(inputdir).filter(_.toPath.toString.endsWith(".csv")).foreach(process)
  
  //import scala.collection.JavaConversions._
  //Files.newDirectoryStream(Paths get dirName).filter(_.toString.endsWith(".csv")).foreach(process(_))

  println(System.currentTimeMillis - time)
}