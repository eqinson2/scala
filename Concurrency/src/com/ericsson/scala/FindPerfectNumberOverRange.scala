package com.ericsson.scala

class FindPerfectNumberOverRange {
  def countPerfectNumberInRange(start: Int, end: Int, isPerfectFinder: Int => Boolean) = {
    val startTime = System.nanoTime()
    val numberOfPerfectNumbers = (0 /: (start to end)) {
      (count, candidate) => if (isPerfectFinder(candidate)) count + 1 else count
    }
    val endTime = System.nanoTime()

    println("Found " + numberOfPerfectNumbers + " Perfect numbers in given range, took " + (endTime - startTime) / 1000000000.0 + " secs")
  }
}

object FindPerfectNumberOverRange extends App {
  val startNumber = 33550300
  val endNumber = 33550400
  val f = new FindPerfectNumberOverRange
  val f1 = new PerfectNumberFinder
  val f2 = new FasterPerfectNumberFinder
  f.countPerfectNumberInRange(startNumber, endNumber, f1.isPerfect)
  f.countPerfectNumberInRange(startNumber, endNumber, f2.isPerfectConcurrent)
}
