package com.ericsson.cmccmmlbatch.bhconnector

abstract class BHConnector(val f: String, val pool: BHConnectorPool) {
  def this(f: String) = {
    this(f, null)
  }

  protected def process(): Boolean

  protected def fireBatchCmd(): Unit

  protected def waitBHFeedback(): Unit

  protected def handleErrorWithoutThrow(reason: String): Unit
}