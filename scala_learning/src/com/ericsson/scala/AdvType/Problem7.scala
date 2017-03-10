package com.ericsson.scala.AdvType

class Closable {
  def close() = println("closed")
}

object Problem7 extends App {
  def func(target: { def close(): Unit } /*, f: { def close(): Unit } => Unit*/ ) = {
    def doSomething(obj: Any) {
      println("Calling toString on the object: " + obj)
    }
    try {
      doSomething(target)
    } catch {
      case ex: Exception => println(ex.getMessage)
    } finally {
      target.close
    }
  }

  def ff(c: Closable): Unit = { c.close }

  func(new Closable)
}
