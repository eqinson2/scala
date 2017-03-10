package com.ericsson.scala

import java.util.Date
//偏函数
object Log {
  def log(date: Date, message: String) = println(date + "----" + message)

  def main(args: Array[String]): Unit = {
    val logWithDateBound = log(new Date, _: String)
    logWithDateBound("message1")
    logWithDateBound("message2")
    logWithDateBound("message3")
  }
}