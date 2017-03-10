package com.ericsson.scala

/**
  * Created by eqinson on 2016/10/8.
  */
object OptionFlatMap extends App {
  val value1 = Some(1)
  //value.map(x => String.valueOf(x.get).foreach(println))
  val value2 = None
  val value3 = Array(value1, value2)
  value1.foreach(println)
  value2.foreach(println)
  value3.foreach(println)
  value3.filter(_.isDefined).foreach(println)

  var scores = Map("Alice" -> 10, "eqinson" -> 20)
  scores = scores + ("ddd" -> 10)
  scores.get("Alice").foreach(println)
}
