package com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd

import com.ericsson.cmccmmlbatch.bhconnector.IConnectorFactory

class PauseTransaction(override val batchFileName: String, override val factory: IConnectorFactory) extends AbstractBatchCommand(batchFileName, factory) {
  override def processCmdSpecific = "111"
}