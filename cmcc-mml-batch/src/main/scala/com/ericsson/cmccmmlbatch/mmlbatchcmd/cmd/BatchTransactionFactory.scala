package com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd

import com.ericsson.cmccmmlbatch.bhconnector.ConnectorFactory
import com.ericsson.cmccmmlbatch.telnetinterface.statemachine.MMLInterfaceState

object BatchTransactionFactory {
  val CREATE_REGEXP = "SET:TASK:FILE,DEF,(.+)".r
  val CANCEL_REGEXP = "SET:TASK:FILE,DEL,(.+)".r
  val PAUSE_REGEXP = "SET:TASK:ACTIONTYPE,STOP:FILE,(.+)".r
  val RESUME_REGEXP = "SET:TASK:ACTIONTYPE,CONTINUE:FILE,(.+)".r
  val QUERY_REGEXP = "GET:TASKSTATUS:File,(.+)".r

  def produce(cmd: String): Option[MMLInterfaceState] = {
    cmd match {
      case CREATE_REGEXP(file) => Some(new CreateTransaction(file, ConnectorFactory()))
      case CANCEL_REGEXP(file) => Some(new CancelTransaction(file, ConnectorFactory()))
      case PAUSE_REGEXP(file) => Some(new PauseTransaction(file, ConnectorFactory()))
      case RESUME_REGEXP(file) => Some(new ResumeTransaction(file, ConnectorFactory()))
      case QUERY_REGEXP(file) => Some(new QueryCommand(file, ConnectorFactory()))
      case _ => None
    }
  }
}