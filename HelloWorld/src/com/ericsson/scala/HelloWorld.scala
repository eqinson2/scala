package com.ericsson.scala

object HelloWorld {
  def main(args: Array[String]): Unit = {
    val greetStrings = new Array[String](3)
    greetStrings(0) = "Hello"
    greetStrings(1) = ","
    greetStrings(2) = "world!\n"
    for (i <- 0.to(2))
      print(greetStrings(i))

    println(1.+(2))

    greetStrings.update(0, "HELLO")
    greetStrings.update(1, ",")
    greetStrings.update(2, "WORLD!\n")
    for (i <- 0 to 2)
      print(greetStrings.apply(i))

    val greetStrings_2 = Array("Hello", ",", "World\n")
    val greetStrings_3 = Array.apply("Hello", ",", "World\n")

    println(greetStrings_2.apply(0))
    println(greetStrings_3.apply(2))

    val result = for (i <- 1 to 10; if i % 2 == 0)
      yield i * 2

    println(result)

    for (i <- 1 to 3; j <- 4 to 6)
      print("[" + i + "," + j + "]")

    val result2 = for (i <- 1 to 10) yield i * 2
    println(result2)
  }

}