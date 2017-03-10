package com.ericsson.scala.Operator

class RichFile2(val path: String) {}

object RichFile2 {
  def apply(path: String): RichFile2 = {
    new RichFile2(path)
  }

  def unapplySeq(richFile: RichFile2): Option[Seq[String]] = {
    if (richFile.path == null || richFile.path.length == 0) {
      None
    } else {
      Some(richFile.path.split("/"))
    }
  }
}

object RichFileTest2 extends App {
  val richFile = RichFile2("/home/cay/readme.txt")
  val RichFile2(r @ _*) = richFile
  println(r)
}

