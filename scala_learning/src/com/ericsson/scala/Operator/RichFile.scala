package com.ericsson.scala.Operator

class RichFile(val path: String) {}

object RichFile {
  def apply(path: String): RichFile = {
    new RichFile(path)
  }

  def unapply(file: RichFile) = {
    val regex = "([/\\w+]+)/(\\w+)\\.(\\w+)".r
    val regex(a, b, c) = file.path
    Some(a, b, c)
  }
}

object RichFileTest1 extends App {
  val richFile = RichFile("/home/eqinson/pgngn/readme.txt")
  val RichFile(r1, r2, r3) = richFile
  println(r1)
  println(r2)
  println(r3)
}