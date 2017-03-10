package com.ericsson.cmccmmlbatch.config

import com.typesafe.config.{Config, ConfigException, ConfigFactory}
import org.apache.logging.log4j.LogManager

object BatchHandlerConfig {
  lazy val config = new BatchHandlerConfig(ConfigFactory.load)
}

class BatchHandlerConfig(configuration: Config) {
  lazy val reportKeepDay = getReportKeepDay
  lazy val outputReportDir = getOutputReportDir
  lazy val batchJobDir = getBatchJobDir
  lazy val batchLogDir = getBatchLogDir
  private val logger = LogManager.getLogger(BatchHandlerConfig)

  private def getReportKeepDay = {
    var reportKeepDay: Option[Int] = None

    try {
      reportKeepDay = Some(configuration.getInt("com.ericsson.mmlbatch.batchhandler.reportKeepDay"))
      logger.info("Found reportKeepDay {} in configuration", reportKeepDay)
    } catch {
      case _: ConfigException =>
        logger.error("No reportKeepDay configuration found.")
    }
    reportKeepDay
  }

  private def getOutputReportDir = {
    var outputReportDir: Option[String] = None

    try {
      outputReportDir = Some(configuration.getString("com.ericsson.mmlbatch.batchhandler.outputReportDir"))
      logger.info("Found outputReportDir {} in configuration", outputReportDir)
    } catch {
      case _: ConfigException =>
        logger.error("No outputReportDir configuration found.")
    }
    outputReportDir
  }

  private def getBatchJobDir = {
    var batchJobDir: Option[String] = None

    try {
      batchJobDir = Some(configuration.getString("com.ericsson.mmlbatch.batchhandler.BatchJobDir"))
      logger.info("Found BatchJobDir {} in configuration", batchJobDir)
    } catch {
      case _: ConfigException =>
        logger.error("No BatchJobDir configuration found.")
    }
    batchJobDir
  }

  private def getBatchLogDir = {
    var batchLogDir: Option[String] = None

    try {
      batchLogDir = Some(configuration.getString("com.ericsson.mmlbatch.batchhandler.BatchLogDir"))
      logger.info("Found BatchLogDir {} in configuration", batchLogDir)
    } catch {
      case _: ConfigException =>
        logger.error("No BatchLogDir configuration found.")
    }
    batchLogDir
  }
}

trait BatchHandlerConfigProvider {
  def BHConfiguration = BatchHandlerConfig.config
}