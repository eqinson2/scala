package com.ericsson.scala.Actors

import scala.actors.Actor
import scala.actors.Actor.receive
import scala.collection.mutable.ArrayBuffer

class ComputeMaxServive extends Actor {
  def act() = {
    receive {
      case MsgRange(seq: IndexedSeq[Int]) =>
        sender ! seq.max
    }
  }
}

sealed case class MsgRange(seq: IndexedSeq[Int])

object ComputeMaxServiceMaster extends App {
  def genRandomArr(n: Int) = {
    var r = new scala.util.Random
    (1 to n).map { _ => r.nextInt(n) }
  }

  val nPiece = Runtime.getRuntime().availableProcessors()
  val ceiling = 1000000
  val seq = genRandomArr(ceiling)

  val start = System.currentTimeMillis()
  val rangeSize = ceiling / nPiece
  for (id <- 0 until nPiece) {
    println("* Sending piece to ComputeAvgServive " + id + "...")
    val range = seq.slice(rangeSize * id, rangeSize * (id + 1))
    val service = new ComputeMaxServive
    service.start
    service ! MsgRange(range)
  }

  var max: Int = _
  for (i <- 0 until nPiece) {
    receive {
      case reply: Int => if (max < reply) max = reply else max
    }
  }

  println(max)
  println(System.currentTimeMillis() - start)

}