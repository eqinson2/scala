package com.ericsson.scala.Operator

class BigSequence(var value: Long) {
  def apply(index: Int): Long = {
    if (index < 63 && index >= 0)
      (value & 1L << index) >> index
    else
      -1
  }

  def update(index: Int, bitValue: Boolean): Unit = {
    if (index < 63 && index >= 0) {
      if (bitValue)
        value = value | (1L << index)
      else
        value = value & ~(1L << index)
    }
  }
}

object BigSequence extends App {
  def apply(v: Long) = new BigSequence(v)

  def toBinary(n: Long): String = n match {
    case 0 | 1 => s"$n"
    case _     => s"${toBinary(n / 2)}${n % 2}"
  }

  val v = BigSequence(1000000)
  for (index <- 0 until 63) {
    println(s"index:${index}")
    println(toBinary(v.value), v(index))
    v(index) = true
    println(v.value, toBinary(v.value), v(index))
  }
}