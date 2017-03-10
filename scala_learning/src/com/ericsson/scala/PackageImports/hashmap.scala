package com.ericsson.scala.PackageImports

object hashmap extends App {
  import java.util.{ HashMap => JHashMap }
  val jmap = new JHashMap[String, String]()

  jmap.put("a", "1")
  jmap.put("b", "2")
  jmap.put("c", "3")

  import scala.collection.mutable.{ HashMap => SHashMap }
  var smap = new SHashMap[String, String]()
  for (k <- jmap.keySet().toArray())
    smap += (k.toString -> jmap.get(k))

  println(smap.mkString("\n"))

}