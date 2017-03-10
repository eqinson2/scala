package com.ericsson.scala

object ListDemo {
  def main(args: Array[String]): Unit = {
    val oneTwo = List(1, 2)
    val threeFour = List(3, 4)
    val oneTwoThreeFour = oneTwo ::: threeFour
    println(oneTwo + " and " + threeFour + " were not mutated.")
    println("Thus, " + oneTwoThreeFour + " is a new list")

    val oneTwo_2 = 1 :: 2 :: Nil
    println(oneTwo_2)
    val oneTwoThree = 1 :: 2 :: 3 :: Nil
    println(oneTwoThree)

    val oneTowThreeFor = (4 :: oneTwoThree).reverse
    println(oneTowThreeFor)

    val pair = (99, "Luftballons")
    println(pair._1)
    println(pair._2)

    val buffer = new scala.collection.mutable.ListBuffer[Int]
    buffer += 1
    buffer += 2
    buffer += 3
    val list = buffer.toList
    println(list)

    var jetSet = scala.collection.mutable.Set("Boeing", "Airbus")
    jetSet += "Lear"
    println(jetSet.contains("Cessna"))

    val romanNumeral = Map(1 -> "I", 2 -> "II",
      3 -> "III", 4 -> "IV", 5 -> "V")
    println(romanNumeral(4))

  }
}