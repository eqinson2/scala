package com.ericsson.cmccmmlbatch.telnetinterface.mml

object MMLConstant {
  val RESP_SUCCESSFUL = "RESP:0;";

  val RESP_REJECT_6002 = "RESP:6002:Wrong Login/Logout state;";

  val RESP_PREFIX = "RESP:";

  val LOGIN = "LOGIN";

  val LOGOUT = "LOGOUT";

  val GET = "GET";

  val SET = "SET";

  val SUCCESS = "0";

  val SYNTAX_ERROR_6000 = "6000:Syntax error";

  val INVALID_LOGIN_6001 = "6001:Failed to login";

  val OPERATION_NOT_SUPPORTED_1004 = "1004";

  val REJECT_6002 = "6002:Wrong Login/Logout state";

  val LOGOUT_REQUEST = "LOGOUT;";

  val STATE_OWNER = "STATE_OWNER";

  val CAI_LOGIN_RETRY_TIMES = "CaiLoginRetryTimes";
}