package com.ericsson.scala.Matches

abstract class Item

case class Articles(description: String, price: Double) extends Item

case class Bundle(description: String, discount: Double, item: Item*) extends Item

case class Multiple(number: Int, items: Item) extends Item

object Items extends App {
  def price(it: Item): Double = it match {
    case Articles(_, p)            => p
    case Bundle(_, disc, its @ _*) => its.map { price _ }.sum - disc
    case Multiple(num, items)      => num * price(items)
  }
}
  
