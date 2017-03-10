package com.ericsson.scala.TraitsDemo

import java.io.{BufferedOutputStream, File, FileOutputStream}
import java.util.Date


trait Logger {
  def log(msg: String)
}

trait TimestampLogger extends Logger {
  abstract override def log(msg: String): Unit = super.log(new Date + ":" + msg)
}

trait BufferedLogger extends Logger {
  override def log(msg: String): Unit = {
    val of = new FileOutputStream(new File("scala.log"))
    val bufferedOutput = new BufferedOutputStream(of)
    bufferedOutput.write(msg.getBytes)
    bufferedOutput.close()
  }
}

trait ShorterLogger extends Logger {
  val maxLength: Int

  abstract override def log(msg: String): Unit = {
    super.log(if (msg.length <= maxLength) msg else msg.substring(0, maxLength - 3) + "...")
  }
}

trait ConsoleLogger extends Logger {
  def log(msg: String): Unit = {
    println(msg)
  }
}

class LoggerTest {
}

object LoggerTest {
  def main(args: Array[String]): Unit = {
    val logger = new LoggerTest with BufferedLogger with TimestampLogger
    logger.log("eqinson")

    val logger2 = new LoggerTest with ConsoleLogger
    logger2.log("EQINSON")

    val logger3 = new LoggerTest with BufferedLogger with TimestampLogger with ShorterLogger {
      val maxLength = 5
    }
    logger3.log("eeeee eeeee eeeee")
  }
}
