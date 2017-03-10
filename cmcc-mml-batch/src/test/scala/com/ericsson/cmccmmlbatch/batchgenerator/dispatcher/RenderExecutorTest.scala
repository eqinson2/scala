package com.ericsson.cmccmmlbatch.batchgenerator.dispatcher

import com.ericsson.cmccmmlbatch.batchgenerator.executor.RenderExecutor
import com.ericsson.cmccmmlbatch.batchgenerator.parser.BatchJobParser
import com.ericsson.cmccmmlbatch.config.BatchFileRenderConfigProvider
import com.ericsson.cmccmmlbatch.utils.IOUtils
import org.apache.logging.log4j.LogManager
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class RenderExecutorTest extends FlatSpec with Matchers with BeforeAndAfter with BatchFileRenderConfigProvider {

  private val logger = LogManager.getLogger(BatchFileRenderTest)

  before {
    IOUtils.delAllFile(RenderConfiguration.outputBatchFileLocation.get)
  }

  after {
    IOUtils.delAllFile(RenderConfiguration.outputBatchFileLocation.get)
  }

  "RenderExecutorTest" should "generate all batch file correctly" in {
    var p = new BatchJobParser("test_batch.txt");
    val number = p.validateFormat
    p.close

    p = new BatchJobParser("test_batch.txt");
    val dispatcher = new RenderExecutor(p, number)

    dispatcher.startup
    dispatcher.waitForComplete
    p.close
  }
}