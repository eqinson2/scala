package com.ericsson.cmccmmlbatch.bhconnector

abstract class InterruptableConnector(override val f: String, override val pool: BHConnectorPool) extends BHConnector(f) with Runnable {
  override protected def process = true

  override protected def fireBatchCmd = {}

  override protected def waitBHFeedback = {}

  override protected def handleErrorWithoutThrow(reason: String) = {}
}

class BHCreator(override val f: String, override val pool: BHConnectorPool) extends InterruptableConnector(f, pool) {
  override def run() {
  }

  override protected def process = true

  override protected def fireBatchCmd = {}

  override protected def waitBHFeedback = {}

  override protected def handleErrorWithoutThrow(reason: String) = {}
}