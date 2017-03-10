package com.ericsson.scala

class Equipment(val routine: Int => Int) {
  private def simulate(input: Int) = {
    println("Running simulation...")
    routine(input)
  }
}

object Equipment {
  def calculator2(input: Int) = { println("Calc with " + input); input }

  def main(args: Array[String]): Unit = {
    val calculator = { input: Int => println("Calc with " + input); input }
    val equipment1 = new Equipment(calculator)
    val equipment2 = new Equipment(calculator)

    equipment1.simulate(4)
    equipment2.simulate(6)

    val equipment3 = new Equipment(calculator2)
    val equipment4 = new Equipment(calculator2)

    equipment3.simulate(4)
    equipment4.simulate(6)
  }
}