package com.ericsson.cmccmmlbatch.config

import com.typesafe.config.{Config, ConfigException, ConfigFactory}
import org.apache.logging.log4j.LogManager

object TelnetServerConfig {
  /** The configuration. */
  lazy val config = new TelnetServerConfig(ConfigFactory.load)

  def firstMessage =
    """CONNECTING TO MMLBatch...
      |PROCESS CONNECTED...
      |Enter command: """.stripMargin

  def NewLineWithPrompt = s"${LS}Enter command: "

  def LS = sys.props("line.separator")
}

class TelnetServerConfig(configuration: Config) {
  lazy val (telnetHost: Option[String], telnetPort: Option[Int]) = hostAndPortFromConfig
  lazy val (mmlUser: Option[String], mmlPassword: Option[String]) = authInfo
  private val logger = LogManager.getLogger(TelnetServerConfig)
  private val TelnetHostPath = "com.ericsson.mmlbatch.telnet.host"
  private val TelnetPortPath = "com.ericsson.mmlbatch.telnet.port"
  private val mmlUserPath = "com.ericsson.mmlbatch.mmlinterface.user"
  private val mmlPasswordPath = "com.ericsson.mmlbatch.mmlinterface.password"

  private def hostAndPortFromConfig: (Option[String], Option[Int]) = {
    var host: Option[String] = None
    var port: Option[Int] = None

    try {
      host = Some(configuration.getString(TelnetHostPath))
      port = Some(configuration.getInt(TelnetPortPath))
      logger.info("Found telnet server host {} and port {} in configuration", host.get, port.get)
    } catch {
      case _: ConfigException =>
        logger.info("No telnet server configuration found.")
    }
    (host, port)
  }

  private def authInfo: (Option[String], Option[String]) = {
    var user: Option[String] = None
    var password: Option[String] = None

    try {
      user = Some(configuration.getString(mmlUserPath))
      password = Some(configuration.getString(mmlPasswordPath))
      logger.info("Found mmlinterface user {} in configuration", user.get)
    } catch {
      case _: ConfigException =>
        logger.info("No telnet server configuration found.")
    }
    (user, password)
  }

}

trait TelnetServerConfigProvider {
  def TelnetConfiguration = TelnetServerConfig.config
}
