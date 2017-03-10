package com.ericsson.scala

import scala.actors._
import Actor._

object PrimeTeller extends App {
  def isPrime(number: Int) = {
    println("Going to find if " + number + " is prime")
    var result = true

    if (number == 2 || number == 3) result = true
    for (i <- 2 to Math.sqrt(number.toDouble).floor.toInt; if result)
      if (number % i == 0) result = false

    println("Done finding if " + number + " is prime")
    result
  }

  val primeTeller = actor {
    var continue = true

    while (continue) {
      receive {
        //case (caller: Actor, number: Int) => caller ! (number, isPrime(number))
        case (caller: Actor, number: Int) => actor { caller ! (number, isPrime(number)) }
        case "quit"                       => continue = false
      }
    }
  }

  primeTeller ! (self, 2)
  primeTeller ! (self, 131)
  primeTeller ! (self, 132)

  for (i <- 1 to 3) {
    receive {
      case (number, result) => println(number + " is prime? " + result)
    }
  }

  primeTeller ! "quit"
}