package com.ericsson.cmccmmlbatch.telnetinterface.statemachine

import com.ericsson.cmccmmlbatch.config.TelnetServerConfigProvider
import com.ericsson.cmccmmlbatch.telnetinterface.mml.MMLConstant
import org.apache.logging.log4j.LogManager

object LoggedIn {
  var instance: Option[LoggedIn] = None

  def apply() = synchronized {
    if (instance.isEmpty) instance = Some(new LoggedIn)
    instance.get
  }
}

class LoggedIn extends MMLInterfaceState with TelnetServerConfigProvider {
  private val logger = LogManager.getLogger(LoggedIn)

  override def stateEnter(stateMachine: MMLInterfaceStateMachine, operation: String, command: String) = {
    logger.debug("Login Request received:" + operation + "|||" + command)

    // login is handled in initial state
    if (LoggedOut() != stateMachine.state)
      MMLConstant.REJECT_6002
    else
      handleLogin(stateMachine, command)
  }

  private def handleLogin(stateMachine: MMLInterfaceStateMachine, command: String) = {
    val commands = command.split(":")

    command.split(":").length match {
      case 3 => simple_authenticate(stateMachine, commands(1), commands(2))
      case _ => logger.error("invalid login request."); MMLConstant.SYNTAX_ERROR_6000
    }
  }

  private def simple_authenticate(stateMachine: MMLInterfaceStateMachine, user: String, passwd: String) = {
    if (user == TelnetConfiguration.mmlUser.get && passwd == TelnetConfiguration.mmlPassword.get) {
      stateMachine.state = this
      MMLConstant.SUCCESS
    } else
      MMLConstant.INVALID_LOGIN_6001
  }

  override def stateExit(stateMachine: MMLInterfaceStateMachine) {}

}