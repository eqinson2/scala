package com.ericsson.scala.TraitsDemo

import java.io.{BufferedOutputStream, File, FileOutputStream}

trait MyLogged {
  def log(msg: String): Unit = {}
}

trait MyConsoleLogger extends MyLogged {
  override def log(msg: String): Unit = {
    println(msg)
  }
}

trait MyTimestampLogger extends MyLogged {
  override def log(msg: String): Unit = {
    super.log(new java.util.Date() + " " + msg)
  }
}

trait MyBufferedLogger extends MyLogged {
  override def log(msg: String): Unit = {
    val of = new FileOutputStream(new File("SavingsAccount.log"))
    val bufferedOutput = new BufferedOutputStream(of)
    bufferedOutput.write(msg.getBytes)
    bufferedOutput.close()
  }
}

trait MyShortLogger extends MyLogged {
  val maxLength = 15

  override def log(msg: String): Unit = {
    super.log(
      if (msg.length <= maxLength) {
        msg
      } else {
        msg.substring(0, maxLength - 3) + "..."
      })
  }
}

trait Account {
  var balance: Int
}

class SavingsAccount extends Account with MyLogged {
  override var balance: Int = 10

  def withdraw(amount: Int): Unit = {
    if (amount > balance) {
      log("Insufficient funds")
    } else {
      balance -= amount
    }
  }
}

object SavingsAccountTest extends App {
  val acct1 = new SavingsAccount with MyBufferedLogger with MyTimestampLogger with MyShortLogger
  acct1.withdraw(200)
  val acct2 = new SavingsAccount with MyConsoleLogger with MyShortLogger with MyTimestampLogger
  acct2.withdraw(300)
}