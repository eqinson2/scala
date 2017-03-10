package com.ericsson.scala.TraitsDemo

trait CryptoLogger {
  def log(msg: String, key: Int = 4): Unit = {
    println(crypt(msg, key))
  }


  def crypt(msg: String, key: Int) = {
    for (i <- msg) yield if (key >= 0) (97 + ((i - 97 + key) % 26)).toChar
    else (97 + ((i - 97 + 26 + key) % 26)).toChar
  }
}

class Application {}

object Application extends App {
  val app = new Application with CryptoLogger
  app.log("eqinson", 37)
}