package com.ericsson.scala

import scala.actors._
import Actor._

object ReactWithin extends App {
  def info = println(Thread.currentThread())

  val caller = self

  def accumulate(sum: Int) {
    reactWithin(500) {
      case number: Int =>
        info; accumulate(sum + number)
      case TIMEOUT =>
        println("Timeout! Will send out result now")
        caller ! sum
    }
    println("This will not be called...")
  }

  val accumulator = actor { accumulate(0) }
  accumulator ! 1
  accumulator ! 7
  accumulator ! 8

  receiveWithin(10000) { case result => println("Total is " + result) }

}
