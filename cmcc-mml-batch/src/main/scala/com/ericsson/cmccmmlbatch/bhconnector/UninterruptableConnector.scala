package com.ericsson.cmccmmlbatch.bhconnector

import java.util.concurrent.Callable

abstract class UninterruptableConnector(f: String) extends BHConnector(f) with Callable[Boolean] {
  override def call: Boolean = true

  override protected def process = true

  override protected def fireBatchCmd = {}

  override protected def waitBHFeedback = {}

  override protected def handleErrorWithoutThrow(reason: String) = {}

}

class BHCancel(f: String) extends UninterruptableConnector(f) {

}

class BHPauser(f: String) extends UninterruptableConnector(f) {

}

class BHResumer(f: String) extends UninterruptableConnector(f) {

}