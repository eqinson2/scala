package com.ericsson.cmccmmlbatch.telnetinterface.mml

import org.apache.logging.log4j.LogManager

object MMLUtil {
  private val logger = LogManager.getLogger(MMLUtil)

  def invalidInput(input: String) = {
    if (input.takeRight(1) != ";") {
      logger.error("command does not end with ; {}", input); true
    }
    if (invalidOperations(input)) true
    false
  }

  private def invalidOperations(input: String) = {
    val command = input.substring(0, input.length() - 1);

    command.split(":")(0) match {
      case MMLConstant.LOGIN | MMLConstant.LOGOUT if invalidLogOperation(command) => true
      case MMLConstant.LOGIN | MMLConstant.LOGOUT => false
      case str if (!Array(MMLConstant.GET, MMLConstant.SET).exists(_ == str)) => true
      case _ => validateOtherOperations(input)
    }
  }

  private def invalidLogOperation(command: String) = {
    val LOGIN_REGEXP = "LOGIN:(.+):(.+)".r
    val LOGOUT_REGEXP = "LOGOUT".r

    command match {
      case LOGIN_REGEXP(user, passwd) => false
      case LOGOUT_REGEXP(_) => false
      case _ => {
        logger.error("unsupported LogOperation {}", command); true
      }
    }
  }

  private def validateOtherOperations(command: String) = {
    val CREATE_REGEXP = "SET:TASK:FILE,DEF,(.+)".r
    val CANCEL_REGEXP = "SET:TASK:FILE,DEL,(.+)".r
    val PAUSE_REGEXP = "SET:TASK:ACTIONTYPE,STOP:FILE,(.+)".r
    val RESUME_REGEXP = "SET:TASK:ACTIONTYPE,CONTINUE:FILE,(.+)".r
    val QUERY_REGEXP = "GET:TASKSTATUS:File,(.+)".r

    command match {
      case CREATE_REGEXP(_) | CANCEL_REGEXP(_) | PAUSE_REGEXP(_) | RESUME_REGEXP(_) | QUERY_REGEXP(_) => false
      case _ => {
        logger.error("unsupported operation {}", command); true
      }
    }
  }
}