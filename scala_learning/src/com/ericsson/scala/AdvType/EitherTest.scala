package com.ericsson.scala.AdvType

object EitherTest extends App {
  def findMostClose(arr: Array[Int], target: Int): Int Either Int = {
    val arrWithIndex = arr.zipWithIndex
    arrWithIndex.find { _._1 == target } match {
      case Some(v) => Left(v._2)
      case None    => Right(arrWithIndex.map(element => (Math.abs(element._1 - target), element._2)).sortWith(_._1 < _._1).head._2)
    }
  }

  val arr: Array[Int] = Array(11, 12, 17, 18, 19)
  findMostClose(arr, 15) match {
    case Left(l)  => println("Answer: " + l)
    case Right(r) => println("Answer: " + r)
  }
}