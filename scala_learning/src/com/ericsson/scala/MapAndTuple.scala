package com.ericsson.scala

object MapAndTuple extends App {

  def func1 {

    val map: Map[String, Float] = Map("basketball" -> 3.0f, "football" -> 2.0f, "tennis" -> 1.0f)
    println(for ((k, v) <- map) yield (k, v / 2))
  }

  func1

  def func2 {
    import scala.collection.mutable.Map
    val words_frequency: Map[String, Int] = Map()

    val in = new java.util.Scanner(new java.io.File("word_statistic.txt"))
    while (in.hasNext) {
      val word = in.next
      words_frequency.get(word) match {
        case Some(count) => words_frequency(word) = count + 1
        case None => words_frequency(word) = 1
      }
    }
    println("===================func2===================")
    for ((k, v) <- words_frequency)
      println(s"word:$k, count:$v")
  }

  func2

  def func3() {
    import scala.collection.immutable.Map
    var words_frequency = Map[String, Int]()

    val in = new java.util.Scanner(new java.io.File("word_statistic.txt"))
    while (in.hasNext()) {
      val word = in.next()
      words_frequency.get(word) match {
        case Some(count) => words_frequency = words_frequency + (word -> (count + 1))
        case None => words_frequency = words_frequency + (word -> 1)
      }
    }
    println("===================func3===================")
    for ((k, v) <- words_frequency)
      println(s"word:$k, count:$v")
  }

  func3

  def func4() {
    import scala.collection.immutable.SortedMap
    var words_frequency: Map[String, Int] = SortedMap()

    val in = new java.util.Scanner(new java.io.File("word_statistic.txt"))
    while (in.hasNext()) {
      val word = in.next()
      words_frequency.get(word) match {
        case Some(count) => words_frequency = words_frequency + (word -> (count + 1))
        case None => words_frequency = words_frequency + (word -> 1)
      }
    }
    println("===================func4===================")
    for ((k, v) <- words_frequency)
      println(s"word:$k, count:$v")
  }

  func4

  def func5() {
    import scala.collection.convert.Wrappers.JMapWrapper
    import scala.collection.mutable.Map
    val words_frequency: Map[String, Int] = JMapWrapper(new java.util.TreeMap[String, Int])

    val in = new java.util.Scanner(new java.io.File("word_statistic.txt"))
    while (in.hasNext()) {
      val word = in.next()
      words_frequency.get(word) match {
        case Some(count) => words_frequency(word) = count + 1
        case None => words_frequency(word) = 1
      }
    }
    println("===================func5===================")
    for ((k, v) <- words_frequency)
      println(s"word:$k, count:$v")
  }

  func5

  def func6() {
    import java.util.Calendar

    import scala.collection.mutable.LinkedHashMap
    val date: scala.collection.mutable.Map[String, Int] = LinkedHashMap[String, Int]()

    date("Monday") = Calendar.MONDAY
    date("Tuesday") = Calendar.TUESDAY
    date("Wednesday") = Calendar.WEDNESDAY
    date("Thursday") = Calendar.THURSDAY
    date("Friday") = Calendar.FRIDAY
    date("Saturday") = Calendar.SATURDAY
    date("Sunday") = Calendar.SUNDAY

    println("===================func6===================")
    for ((k, v) <- date)
      println(s"Date:$k, date:$v")
  }

  func6

  def func7() {
    import scala.collection.JavaConversions.propertiesAsScalaMap
    val props: scala.collection.Map[String, String] = System.getProperties()

    val maxWidth = (0 /: props.keySet.map { _.length }) ((carryOver, elem) => Math.max(carryOver, elem))

    println("===================func7===================")
    for ((k, v) <- props)
      println(k + " " * (maxWidth - k.length) + "|" + v)
  }

  func7

  def minmax(values: Array[Int]) = {
    ((0 /: values) ((a, b) => Math.min(a, b)), (0 /: values) ((a, b) => Math.max(a, b)))
  }

  println(minmax(Array(1, 2, 3, 4, 5, 6, 7, 8, 9)))

  def lteqgt(values: Array[Int], v: Int) = {
    (values.filter { s => s < v }.length, values.filter { s => s == v }.length, values.filter { s => s > v }.length)

  }

  println(lteqgt(Array(-1, -2, -3, 4, 5, 6, 7, 8, 9), 4))

  val keys = Array(1, 2, 3)
  val values = Array("1", "2", "3")
  for ((k, v) <- keys.zip(values))
    println(s"$k=$v")

  println("Hello" zip ("World"))
  println(List(1, 2, 3).zipWithIndex)

}