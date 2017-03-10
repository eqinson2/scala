package com.ericsson.scala.ClassExtends

class Item private(val description: String, val price: Int) {
  override def equals(other: Any): Boolean = {
    if (!other.isInstanceOf[Item]) return false
    val that = other.asInstanceOf[Item]
    if (that == null) false
    else description == that.description && price == that.price
  }
}

object Item extends App {
  def apply(description: String, price: Int) = new Item(description, price)

  println(Item("aaa", 1) == Item("aaa", 1)) //true
  println(Item("aaa", 1) equals Item("aaa", 1)) // true
  println(Item("aaa", 1) eq Item("aaa", 1)) //false

  println(Item("aaa", 1) == Item("aaa", 2)) //false
  println(Item("aaa", 1) equals Item("aaa", 2))
  //false

  val a = Item("bbb", 3)
  val b = a
  println(b eq a) //true
  println(b == a) //true
  println(b equals a) //true

}