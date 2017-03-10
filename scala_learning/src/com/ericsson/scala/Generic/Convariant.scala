package com.ericsson.scala.Generic

//class PairConvariant[+T](var first: T, var second: T)

class PairConvariant[+T](val first: T, val second: T) {
  //def replaceFirst(newFirst: T) = new PairConvariant[T](newFirst, second)
  def replaceFirst[R >: T](newFirst: R) = new PairConvariant[R](newFirst, second)
}

//class PairConvariant2[+T](var first: T, val second: T) {
//  def replaceFirst(newFirst: T) = new PairConvariant[T](newFirst, second)
//  def replaceFirst[R >: T](newFirst: R) = { first = newFirst }
//}

class PairConvariant3[+T](val first: T, val second: T) {
  //def replaceFirst(newFirst: T) = new PairConvariant[T](newFirst, second)
  def replaceFirst[R >: T](newFirst: R) = new PairConvariant[R](newFirst, second)
  override def toString = { first + " " + second }
}

class NastyDoublePair[Double](override val first: Double, override val second: Double)
    extends PairConvariant3[Double](first, second) {
  //type mismatch; found : Double(in class NastyDoublePair) required: scala.Double
  //override def replaceFirst(newFirst: Double) = new PairConvariant3(math.sqrt(newFirst), second) //ERROR
}

object Convariant extends App {
  val v = new PairConvariant3[Double](1.0, 2.0)
  println(v.replaceFirst("String").first)
}