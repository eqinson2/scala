package ch4

object LazyTest extends App {
  lazy val x = { println("initializing x"); "done" }
  println("xxxxxxxxxxxxxxxxxxxxxxxxxxx")
  println(x)
  println(x)
}