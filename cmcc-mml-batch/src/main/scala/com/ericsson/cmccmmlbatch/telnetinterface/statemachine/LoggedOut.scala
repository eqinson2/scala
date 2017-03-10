package com.ericsson.cmccmmlbatch.telnetinterface.statemachine

import com.ericsson.cmccmmlbatch.telnetinterface.mml.MMLConstant
import org.apache.logging.log4j.LogManager

object LoggedOut {
  var instance: Option[LoggedOut] = None

  def apply() = synchronized {
    if (instance.isEmpty) instance = Some(new LoggedOut)
    instance.get
  }
}

class LoggedOut extends MMLInterfaceState {
  private val logger = LogManager.getLogger(LoggedOut)

  override def stateEnter(stateMachine: MMLInterfaceStateMachine, operation: String, command: String): String = {
    if (LoggedOut() == stateMachine.state)
      return MMLConstant.REJECT_6002

    stateMachine.state = this
    MMLConstant.SUCCESS
  }

  override def stateExit(stateMachine: MMLInterfaceStateMachine) {}
}