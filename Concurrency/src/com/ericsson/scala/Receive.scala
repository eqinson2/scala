package com.ericsson.scala

import scala.actors._
import Actor._

object Receive extends App {
  val caller = self

  val accumulator = actor {
    var sum = 0
    var continue = true
    while (continue) {
      //sum += receive {
      sum += receiveWithin(1000) {
        case number: Int => number
        case "quit" =>
          continue = false
          0
      }
    }
    caller ! sum
  }

  accumulator ! 1
  accumulator ! 7
  accumulator ! 8
  accumulator ! "quit"

  receiveWithin(10000) { case result => println("Total is " + result) }

}