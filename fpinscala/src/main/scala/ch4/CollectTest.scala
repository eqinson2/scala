package ch4

object CollectTest extends App {
  println(Some(1).collect { case n if n > 0 => n + 1; case _ => 0 })
  //println(None.collect { case n if n > 0 => n + 1; case _ => 0 })

  val x: Option[Int] = None
  println(x collect { case n if n > 0 => n + 1; case _ => 0 })
}