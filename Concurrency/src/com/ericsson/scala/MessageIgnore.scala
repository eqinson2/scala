package com.ericsson.scala

import scala.actors._
import Actor._

object MessageIgnore extends App {
  val caller = self

  val expectStringOrInteger = actor {
    for (i <- 1 to 4) {
      receiveWithin(1000) {
        case str: String => println("You said " + str)
        case num: Int    => println("You gave " + num)
        case TIMEOUT     => println("Timed out!")
      }
    }
  }

  expectStringOrInteger ! "only constant is change"
  expectStringOrInteger ! 1024
  expectStringOrInteger ! 22.22
  expectStringOrInteger ! (self, 1024)

  receiveWithin(5000) { case _ => println("Abort") }

}