package com.ericsson.scala

import scala.actors.Actor._

class FasterPerfectNumberFinder {
  def sumOfFactorsInRange(lower: Int, upper: Int, number: Int) = {
    (0 /: (lower to upper)) { (sum, i) => if (number % i == 0) sum + i else sum }
  }

  def isPerfectConcurrent(candidate: Int) = {
    val RANGE = 1000000
    val numberOfPartitions = (candidate.toDouble / RANGE).ceil.toInt
    val caller = self

    for (i <- 0 until numberOfPartitions) {
      val lower = i * RANGE + 1
      val upper = candidate min (i + 1) * RANGE

      actor {
        caller ! sumOfFactorsInRange(lower, upper, candidate)
      }
    }

    val sum = (0 /: (0 until numberOfPartitions)) { (partialSum, i) =>
      receive {
        case sumInRange: Int => partialSum + sumInRange
      }
    }
    2 * candidate == sum
  }
}