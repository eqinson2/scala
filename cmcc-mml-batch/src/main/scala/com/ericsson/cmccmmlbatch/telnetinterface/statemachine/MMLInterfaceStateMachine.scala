package com.ericsson.cmccmmlbatch.telnetinterface.statemachine

import java.util.StringTokenizer

import com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd.BatchTransactionFactory
import com.ericsson.cmccmmlbatch.telnetinterface.mml.MMLConstant
import org.apache.logging.log4j.LogManager

trait MMLInterfaceState {
  def stateEnter(stateMachine: MMLInterfaceStateMachine, operation: String, command: String): String = {
    ""
  }

  def stateExit(stateMachine: MMLInterfaceStateMachine) = {}
}

object MMLInterfaceStateMachine {
  var instance: Option[MMLInterfaceStateMachine] = None

  def apply() = synchronized {
    if (instance.isEmpty) instance = Some(new MMLInterfaceStateMachine)
    instance
  }
}

class MMLInterfaceStateMachine private extends MMLInterfaceState {
  private val logger = LogManager.getLogger(MMLInterfaceStateMachine)

  var state: MMLInterfaceState = LoggedOut()

  def processCommand(command: String) = {
    val operation = new StringTokenizer(command, ":").nextToken()

    val newState = getState(operation, command)
    state.stateExit(this)

    if (newState.isDefined)
      newState.get.stateEnter(this, operation, command)
    else
      MMLConstant.OPERATION_NOT_SUPPORTED_1004
  }

  def getState(operation: String, command: String) = {
    operation match {
      case MMLConstant.LOGIN => Some(LoggedIn())
      case MMLConstant.LOGOUT => Some(LoggedOut())
      case MMLConstant.SET | MMLConstant.GET => BatchTransactionFactory.produce(command)
      case _ => None
    }
  }
}