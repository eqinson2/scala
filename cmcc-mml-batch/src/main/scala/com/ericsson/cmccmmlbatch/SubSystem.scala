package com.ericsson.cmccmmlbatch

import com.ericsson.cmccmmlbatch.exception.{ShutdownFailureException, StartupFailureException}

trait SubSystem {
  @throws(classOf[StartupFailureException])
  def startup()

  @throws(classOf[ShutdownFailureException])
  def shutdown(theGracefulShutdownMode: Boolean)

  def isRunning: Boolean

  def getName: String
}