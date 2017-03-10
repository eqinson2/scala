package com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd

import java.io.File

import com.ericsson.cmccmmlbatch.batchgenerator.executor.RenderExecutor
import com.ericsson.cmccmmlbatch.batchgenerator.parser.BatchJobParser
import com.ericsson.cmccmmlbatch.bhconnector.IConnectorFactory
import com.ericsson.cmccmmlbatch.config.BatchFileRenderConfigProvider
import com.ericsson.cmccmmlbatch.exception.{ShutdownFailureException, StartupFailureException}
import com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine.{BatchStateException, TransitMachine}
import com.ericsson.cmccmmlbatch.utils.IOUtils
import org.apache.logging.log4j.LogManager

object CreateTransaction

class CreateTransaction(override val batchFileName: String, override val factory: IConnectorFactory)
  extends AbstractBatchCommand(batchFileName, factory) with BatchFileRenderConfigProvider {
  private val logger = LogManager.getLogger(CreateTransaction)

  override def processCmdSpecific = {
    if (!TransitMachine().canCreate) TransitMachine().create

    import GlobalBatchJobInfo.State._
    if (GlobalBatchJobInfo.getReporterState == RUNNING) {
      logger.info("Still generating report, can not create new task now.")
      throw new BatchStateException(1006)
    }

    if (!isBatchExist) {
      logger.info("Batch file specified does not exist.")
      throw new BatchStateException(1005)
    }

    val p = new BatchJobParser(batchFileName)
    if (System.getProperty("Test") == null && p.validateFormat == -1) {
      throw new BatchStateException(1007)
    }
    val number = p.validateFormat
    p.close

    logger.info("CreateTransaction start...")
    val enterTime = System.currentTimeMillis()

    // clean up output/batchjob/LogDir each time
    IOUtils.cleanup

    val dispatcher = new RenderExecutor(new BatchJobParser(batchFileName), number)
    try {
      dispatcher.startup()
    } catch {
      case e: StartupFailureException => logger.error(e.getMessage)
    }

    GlobalBatchJobInfo.setDispatcherState(RUNNING)

    /**
      * synchronously render batch file so that boss are unable to suspend render task
      */
    dispatcher.waitForComplete
    p.close
    try {
      dispatcher.shutdown(true)
    } catch {
      case e: ShutdownFailureException => logger.error("shutdown dispatcher, already shutdown!", e)
    }
    GlobalBatchJobInfo.setDispatcherState(FINISH)

    logger.info(batchFileName + " has sucessfully render batch files!")

    TransitMachine().create

    logger.info("CreateTransaction finish after " + String.valueOf(System.currentTimeMillis() - enterTime) + "ms...")
    logger.info("But create job is probably running in seperate thread...")

    s"0:FILE,${this.batchFileName}TASK_ID,${GlobalBatchJobInfo.getTaskId}"
  }

  private def isBatchExist = {
    val dir = RenderConfiguration.inputBatchFileLocation.getOrElse("./")
    var fn: String = ""

    if (!dir.endsWith(File.separator)) fn = dir + File.separator + this.batchFileName
    else fn = dir + this.batchFileName

    val file = new File(fn)
    if (file.exists && !file.isDirectory) true
    else false
  }

}