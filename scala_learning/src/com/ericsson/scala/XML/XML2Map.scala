package com.ericsson.scala.XML

object XML2Map extends App {
  var map = Map[String, String]()
  val xml = <dl>
              <dt>A</dt><dd>1</dd>
              <dt>B</dt><dd>2</dd>
              <dt>C</dt><dd>3</dd>
              <dt>D</dt><dd>4</dd>
              <dt>E</dt><dd>5</dd>
            </dl>
  val regex = "<dt>([^<]+)</dt><dd>([^<]+)</dd>".r
  for (regex(k, v) <- regex.findAllIn(xml.toString()))
    map += (k -> v)
  println(map)

  def dl2map(input: scala.xml.Elem): Map[String, String] = {
    var map2 = Map[String, String]()
    val keys = input \ "dt"
    val values = input \ "dd"
    for (i <- 0 until keys.size) map2 += keys(i).text -> values(i).text
    map2
  }
  val input = <dl><dt>A</dt><dd>1</dd><dt>B</dt><dd>2</dd></dl>
  val obj = dl2map(input)
  println(obj)
}