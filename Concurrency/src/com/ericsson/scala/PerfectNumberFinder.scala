package com.ericsson.scala

class PerfectNumberFinder {
  def sumOfFactors(number: Int) = {
    (0 /: (1 to number)) { (sum, i) => if (number % i == 0) sum + i else sum }
  }

  def isPerfect(candidate: Int) = { 2 * candidate == sumOfFactors(candidate) }
}

