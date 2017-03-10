package com.ericsson.proclog.analyst

import java.io.File

import scala.io.Source

object LogAnalystMain extends App {
  val time = System.currentTimeMillis
  val dirName = "pcrf_c3"
  val outputDirName = "out_" + dirName
  val inputDir = new File(dirName)
  val outDir = new File(outputDirName);
  Utils deleteOutputDir outDir
  Utils createOutputDirectory outDir

  val process = (file: File) => AnalysisWork(Source.fromFile(file.toPath.toString).getLines().mkString("\n")).analyze
  Utils.getRecursively(inputDir).filter(_.toPath.toString.endsWith(".csv")).foreach(process)
  
  //import scala.collection.JavaConversions._
  //Files.newDirectoryStream(Paths get dirName).filter(_.toString.endsWith(".csv")).foreach(process(_))

  println(System.currentTimeMillis - time);
}