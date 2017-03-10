package com.ericsson.scala.TraitsDemo

trait Fly {
  def fly(): Unit = {
    println("flying")
  }

  def flywithnowing()
}

trait Walk {
  def walk(): Unit = {
    println("walk")
  }
}

abstract class Bird {
  var name: String
}

class BlueBird extends Bird with Fly with Walk {
  override var name: String = "BlueBird"

  def flywithnowing(): Unit = {
    println(s"$name flywithnowing")
  }

  override def walk(): Unit = {
    println(s"$name walk")
  }
}

object Test extends App {
  val b = new BlueBird()
  b.walk()
  b.flywithnowing()
  b.fly()
}