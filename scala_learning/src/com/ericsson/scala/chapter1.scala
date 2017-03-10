package com.ericsson.scala

object chapter1 extends App {
  println("crazy" * 3)
  println((List[String]() /: List("a", "b", "c")) ((a, b) => b :: a))
  println((0 /: (1 to 10).toList) ((a, b) => b + a))
  println((3 to 10) map (_ % 2 == 0))
  println((3 to 10) map (_ * 2))
  println((3 to 10).exists(_ > 11))
  println(10 max 2)
  println(BigInt(2).pow(1024))
  println(scala.math.sqrt(300))

  import scala.math.BigInt._
  import scala.util.Random

  println(probablePrime(10, Random))

  println(scala.math.BigInt(Random.nextInt).toString(36))

  println("Hello" (0), "Hello" take 1, "Hello" reverse 0, "Hello".takeRight(1), "Hello".drop(2), "Hello".dropRight(2), "Hello".substring(1, 3))
}