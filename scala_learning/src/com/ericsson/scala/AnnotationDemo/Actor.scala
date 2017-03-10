package com.ericsson.scala.AnnotationDemo

import scala.actors.Actor

class T1(obj: Obj) extends Actor {
  def act() {
    println("T1 is waiting")
    Thread.sleep(5000)
    obj.flag = true
    println("T1 set flag = true")
  }
}

class T2(obj: Obj) extends Actor {
  def act() {
    var f = true
    while (f) {
      if (obj.flag) {
        println("T2 is end")
        f = false
      } else {
        println("T2 is waiting")
      }
    }
  }
}

class Obj {
  @volatile var flag: Boolean = false
}

object Test {
  def main(args: Array[String]) {
    val obj = new Obj()
    val t1 = new T1(obj)
    val t2 = new T2(obj)
    t1.start
    t2.start
  }
}