package com.ericsson.cmccmmlbatch.exception

class StartupFailureException(val message: String) extends Exception(message) {
  def this() = this("")
}

class ShutdownFailureException(val message: String) extends Exception(message) {
  def this() = this("")
}

class OperationNotSupportedExceptio(val message: String) extends Exception(message) {
  def this() = this("")
}

class BatchHandlerException(val message: String) extends Exception(message) {
  def this() = this("")
}