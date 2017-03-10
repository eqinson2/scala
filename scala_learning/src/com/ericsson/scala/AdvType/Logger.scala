package com.ericsson.scala.AdvType

trait LoggerComponent {
  trait Logger {
    def log(msg: String)
  }

  val logger: Logger

  class FileLogger(file: String) extends Logger {
    override def log(msg: String) {

    }
  }
}

trait AuthComponent {
  this: LoggerComponent =>

  trait Auth {
    def login(id: String, password: String): Boolean
  }
  
  val auth: Auth

  class MockAuth(file: String) extends Auth {
    override def login(id: String, password: String) = {
      false
    }
  }
}

object AppComponents extends LoggerComponent with AuthComponent {
  val logger = new FileLogger("test.log")
  val auth = new MockAuth("user.txt")
}

