package com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd

import java.util.concurrent.locks.ReentrantLock

import com.ericsson.cmccmmlbatch.bhconnector.IConnectorFactory
import com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine.BatchStateException
import com.ericsson.cmccmmlbatch.telnetinterface.mml.MMLConstant
import com.ericsson.cmccmmlbatch.telnetinterface.statemachine.{LoggedOut, MMLInterfaceState, MMLInterfaceStateMachine}
import org.apache.logging.log4j.LogManager

object TransactionLock {
  val lock = new ReentrantLock()
}

object AbstractBatchCommand

abstract class AbstractBatchCommand(val batchFileName: String, val factory: IConnectorFactory) extends MMLInterfaceState {
  private val logger = LogManager.getLogger(AbstractBatchCommand)

  override def stateEnter(stateMachine: MMLInterfaceStateMachine, operation: String, input: String) = {
    // has not logged in
    if (LoggedOut() == stateMachine.state) MMLConstant.REJECT_6002

    stateMachine.state = this
    process(operation, input)
  }

  private def process(operation: String, cmd: String): String = {
    logger.debug("Batch Command received:" + operation + "|||" + cmd)
    try {
      TransactionLock.lock.lock()
      processCmdSpecific
    } catch {
      case e: BatchStateException =>
        logger.error(e.getErrorMessage)
        s"${e.getErrorCode}:FILE,$batchFileName;${e.getErrorMessage}"
    } finally {
      TransactionLock.lock.unlock()
    }
  }

  override def stateExit(stateMachine: MMLInterfaceStateMachine) {
  }

  protected def processCmdSpecific: String
}