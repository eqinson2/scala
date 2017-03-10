package com.ericsson.cmccmmlbatch.config

import com.typesafe.config.{Config, ConfigException, ConfigFactory}
import org.apache.logging.log4j.LogManager

object BatchFileRenderConfig {
  lazy val config = new BatchFileRenderConfig(ConfigFactory.load)
}

class BatchFileRenderConfig(configuration: Config) {
  lazy val inputBatchFileLocation = getInputBatchFileLocation
  lazy val outputBatchFileLocation = getOutputBatchFileLocation
  lazy val maxSubscriberPerInputFile = getMaxSubscriberPerInputFile
  lazy val batchProvEndPoint = getBatchProvEndPoint
  lazy val pgUserName = getPgUserName
  lazy val pgPasswd = getPgPasswd
  lazy val maxCommandPerOutputBatchFile = getMaxCommandPerOutputBatchFile
  private val logger = LogManager.getLogger(BatchFileRenderConfig)

  def getBatchProvEndPoint = {
    var batchProvEndPoint: Option[String] = None

    try {
      batchProvEndPoint = Some(configuration.getString("com.ericsson.mmlbatch.batchRender.BatchProvEndPoint"))
      logger.info("Found BatchProvEndPoint {} in configuration", batchProvEndPoint)
    } catch {
      case _: ConfigException =>
        logger.error("No BatchProvEndPoint configuration found.")
    }
    batchProvEndPoint
  }

  def getPgUserName = {
    var pgUserName: Option[String] = None

    try {
      pgUserName = Some(configuration.getString("com.ericsson.mmlbatch.batchRender.PgUserName"))
      logger.info("Found PgUserName {} in configuration", pgUserName)
    } catch {
      case _: ConfigException =>
        logger.error("No PgUserName configuration found.")
    }
    pgUserName
  }

  def getPgPasswd = {
    var pgPasswd: Option[String] = None

    try {
      pgPasswd = Some(configuration.getString("com.ericsson.mmlbatch.batchRender.PgPasswd"))
      logger.info("Found outputBatchFileLocation {} in configuration", pgPasswd)
    } catch {
      case _: ConfigException =>
        logger.error("No PgPasswd configuration found.")
    }
    pgPasswd
  }

  def getMaxCommandPerOutputBatchFile = {
    var maxCommandPerBatchFile: Option[Int] = None

    try {
      maxCommandPerBatchFile = Some(configuration.getInt("com.ericsson.mmlbatch.batchRender.MaxCommandPerOutputBatchFile"))
      logger.info("Found MaxCommandPerBatchFile {} in configuration", maxCommandPerBatchFile)
    } catch {
      case _: ConfigException =>
        logger.error("No MaxCommandPerBatchFile configuration found.")
    }
    maxCommandPerBatchFile
  }

  private def getMaxSubscriberPerInputFile = {
    var maxSubscriberPerInputFile: Option[Int] = None

    try {
      maxSubscriberPerInputFile = Some(configuration.getInt("com.ericsson.mmlbatch.batchRender.MaxSubscriberPerInputFile"))
      logger.info("Found maxSubscriberPerInputFile {} in configuration", maxSubscriberPerInputFile)
    } catch {
      case _: ConfigException =>
        logger.error("No reportKeepDay configuration found.")
    }
    maxSubscriberPerInputFile
  }

  private def getInputBatchFileLocation = {
    var inputBatchFileLocation: Option[String] = None

    try {
      inputBatchFileLocation = Some(configuration.getString("com.ericsson.mmlbatch.batchRender.InputBatchFileLocation"))
      logger.info("Found inputBatchFileLocation {} in configuration", inputBatchFileLocation)
    } catch {
      case _: ConfigException =>
        logger.error("No inputBatchFileLocation configuration found.")
    }
    inputBatchFileLocation
  }

  private def getOutputBatchFileLocation = {
    var outputBatchFileLocation: Option[String] = None

    try {
      outputBatchFileLocation = Some(configuration.getString("com.ericsson.mmlbatch.batchRender.OutputBatchFileLocation"))
      logger.info("Found outputBatchFileLocation {} in configuration", outputBatchFileLocation)
    } catch {
      case _: ConfigException =>
        logger.error("No outputBatchFileLocation configuration found.")
    }
    outputBatchFileLocation
  }
}

trait BatchFileRenderConfigProvider {
  def RenderConfiguration = BatchFileRenderConfig.config
}