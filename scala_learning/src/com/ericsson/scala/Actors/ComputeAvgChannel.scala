package com.ericsson.scala.Actors

import scala.collection.mutable.ArrayBuffer
import scala.actors.Actor
import scala.actors.Actor.receive
import scala.actors.OutputChannel
import scala.actors.Channel

class ComputeAvgServiveChannel extends Actor {
  def act() = {
    receive {
      case MsgRangeChannel(seq, out) =>
        out ! seq.sum
    }
  }
}

sealed case class MsgRangeChannel(seq: IndexedSeq[Int], result: OutputChannel[Int])

object ComputeAvgChannel extends App {
  def genRandomArr(n: Int) = {
    var r = new scala.util.Random
    (1 to n).map { _ => r.nextInt(n) }
  }

  val n = Runtime.getRuntime().availableProcessors()
  val max = 10000000
  val services: ArrayBuffer[ComputeAvgServiveChannel] = new ArrayBuffer[ComputeAvgServiveChannel](n)
  val seq = genRandomArr(max)

  val start = System.currentTimeMillis()
  val channel = new Channel[Int]
  for (range <- seq.grouped(max / n)) {
    val service = new ComputeAvgServiveChannel
    service.start
    service ! MsgRangeChannel(range, channel)
    services += service
  }

  var sum1 = 0
  for (i <- 0 until services.length) {
    channel.receive { case reply: Int => sum1 += reply }
  }

  println(n, sum1, sum1 / seq.length)
  println(System.currentTimeMillis() - start)

}