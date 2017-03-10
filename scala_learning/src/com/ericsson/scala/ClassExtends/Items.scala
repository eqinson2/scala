package com.ericsson.scala.ClassExtends

import scala.collection.mutable.ArrayBuffer

abstract class Items {
  def price: Double
  def description: String
}

class SimpleItems(override val price: Double, override val description: String) extends Items {
}

class Bundle() extends Items {
  val stuffs: ArrayBuffer[Items] = new ArrayBuffer[Items]()

  def putStuff(item: SimpleItems): Unit = stuffs.+=(item)
  def remvoveStuff(item: SimpleItems): Unit = stuffs.-=(item)

  override def price = { (0.0D /: (stuffs))((a, b) => a + b.price) }

  override def description = { ("all description:" /: (stuffs))((a, b) => a + b.description + " ") }
}

object Tests extends App {
  val bundle = new Bundle
  bundle.putStuff(new SimpleItems(1.0, "Apple"))
  bundle.putStuff(new SimpleItems(2.0, "Orange"))
  bundle.putStuff(new SimpleItems(3.0, "Peach"))
  println(bundle.price)
  println(bundle.description)
}