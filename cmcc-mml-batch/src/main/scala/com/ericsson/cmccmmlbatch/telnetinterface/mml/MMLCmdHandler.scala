package com.ericsson.cmccmmlbatch.telnetinterface.mml

import com.ericsson.cmccmmlbatch.telnetinterface.statemachine.MMLInterfaceStateMachine
import org.apache.logging.log4j.LogManager

object MMLCmdHandler

class MMLCmdHandler {
  private val logger = LogManager.getLogger(MMLCmdHandler)

  def handleInput(message: String): String = {
    logger.info("It receieved:" + message)
    val result = message match {
      case "\r\n" | "\r" | "\n" => MMLConstant.SYNTAX_ERROR_6000
      case _ =>
        var inputTrimed = removeWhiteSpace(message)
        if (MMLUtil.invalidInput(inputTrimed)) return MMLConstant.SYNTAX_ERROR_6000

        // Remove last character, ; from the input string
        inputTrimed = inputTrimed.substring(0, inputTrimed.length - 1)
        MMLInterfaceStateMachine().get.processCommand(inputTrimed)

    }
    val response = MMLConstant.RESP_PREFIX + result + ";"
    logger.info("It will return:" + response)
    response
  }

  private def removeWhiteSpace(input: String) = {
    val spaceColon = "[ \t]+:".r
    val colonSpace = ":[ \t]+".r
    val spaceComma = "[ \t]+,".r
    val commaSpace = ",[ \t]+".r
    val spaceSemicolon = "[ \t]+;".r
    val semicolonSpace = ";[\r\n \t]+".r
    val beginningSpace = "\\A[ \t]+".r

    var result = spaceColon.replaceAllIn(input, ":")
    result = colonSpace.replaceAllIn(result, ":")
    result = spaceComma.replaceAllIn(result, ",")
    result = commaSpace.replaceAllIn(result, ",")
    result = spaceSemicolon.replaceAllIn(result, ";")
    result = semicolonSpace.replaceAllIn(result, ";")
    beginningSpace.replaceAllIn(result, "")
  }
}