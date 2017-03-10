package com.ericsson.scala

/**
 * @author: Lenovo(2015-04-10 15:48)
 */
object Trait extends App {
  val acct1 = new SavingsAccount with ConsoleLogger with TimestampLogger with ShortLogger
  acct1.withdraw(200)
  val acct2 = new SavingsAccount with ConsoleLogger with ShortLogger with TimestampLogger
  acct2.withdraw(300)
}

trait Logged {
  def log(msg: String) {}
}

trait ConsoleLogger extends Logged {
  override def log(msg: String) {
    println(msg)
  }
}

// 给日志消息加上时间
trait TimestampLogger extends Logged {
  override def log(msg: String) {
    println(">>>>>>>>>TimestampLogger")
    super.log(new java.util.Date() + " " + msg)
  }
}

// 截断过长的的日志
trait ShortLogger extends Logged {
  val maxLength = 15

  override def log(msg: String) {
    println(">>>>>>>>>ShortLogger")
    super.log(
      if (msg.length <= maxLength) {
        msg
      } else {
        msg.substring(0, maxLength - 3) + "..."
      })
  }
}

class Account {
  var balance = 199
}

class SavingsAccount extends Account with ConsoleLogger {
  def withdraw(amount: Double) {
    if (amount > balance) {
      log("Insufficient funds")
    } else {
      balance -= balance
    }
  }
}