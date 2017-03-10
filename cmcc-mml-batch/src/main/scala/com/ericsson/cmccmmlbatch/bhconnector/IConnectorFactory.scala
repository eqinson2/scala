package com.ericsson.cmccmmlbatch.bhconnector

import java.util.concurrent.Callable

trait IConnectorFactory {
  def produceCreator(file: String, pool: BHConnectorPool): Runnable

  def produceCancel(file: String): Callable[Boolean]

  def producePauser(file: String): Callable[Boolean]

  def produceResumer(file: String): Callable[Boolean]
}