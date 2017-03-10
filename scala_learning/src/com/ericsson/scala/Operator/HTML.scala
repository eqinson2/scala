package com.ericsson.scala.Operator

class Table {

  var s: String = ""

  def |(str: String): Table = {
    val t = Table()
    t.s = this.s + "<td>" + str + "</td>"
    t
  }

  def ||(str: String): Table = {
    val t = Table()
    t.s = this.s + "</tr><tr><td>" + str + "</td>"
    t
  }

  override def toString(): String = {
    "<table><tr>" + this.s + "</tr></table>"
  }
}

object Table {
  def apply(): Table = {
    new Table()
  }

  def main(args: Array[String]) {
    println(Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM,.NET")
  }
}