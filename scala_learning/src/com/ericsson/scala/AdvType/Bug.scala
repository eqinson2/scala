package com.ericsson.scala.AdvType

object bugsy extends App {
  val show = () => println("Bug show")
  val move = (distance: Int) => { println(s"Bug move ${distance}"); this }
  val turn = (obj: around.type) => { println("Bug turn"); this }
  val and = (f: () => Unit) => this
  val then = () => {}
  object around
  bugsy move 4 and show and then move 6 and show turn around move 5 and show and then move 4 and show and then move 6 and show turn around move 5 and show
}

 