package com.ericsson.scala.ClassExtends

class Creature {
  val range: Int = 10
  def range2: Int = 101
  val env: Array[Int] = new Array[Int](range2)
  println(range)
  println(range2)
}

class Ant extends Creature {
  override val range: Int = 2
  override val range2: Int = 20
}

class Ant2 extends {
  override val range: Int = 2
  override val range2: Int = 20
} with Creature

class Ant3 extends Creature {
  override val range: Int = 200
}

object Test extends App {
  new Ant
  new Ant2
  new Ant3
}