package com.ericsson.scala.Class

class Counter {
  private var value = Int.MaxValue - 3
  def increment() = {
    if (value + 1 != Int.MaxValue)
      value += 1
    value
  }
  def current = value
}

object Counter extends App {
  val myCounter = new Counter
  myCounter.current
  myCounter.increment()
  print(myCounter.current)
}