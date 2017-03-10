package com.ericsson.cmccmmlbatch.batchgenerator.render

import java.io.{File, PrintWriter}
import java.nio.file.Paths

import com.ericsson.cmccmmlbatch.config.BatchFileRenderConfigProvider
import com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd.GlobalBatchJobInfo
import org.apache.logging.log4j.LogManager

object BatchFileWriterUtils {
  def apply() = new BatchFileWriterUtils
}

class BatchFileWriterUtils extends BatchFileRenderConfigProvider {
  private val logger = LogManager.getLogger(BatchFileWriterUtils)

  def writeToFile(content: String, id: Int) = {
    val bfPath = Paths.get(generateOutputBatchFileName(id))
    if (bfPath.toFile.exists) {
      logger.warn(bfPath.toString + " already exists, has to delete it first!")
      bfPath.toFile.delete
    } else if (bfPath.getParent == null) {
      logger.error(bfPath.toString + " as no parent, BUG!")
    } else if (!bfPath.getParent.toFile.exists) {
      bfPath.getParent.toFile.mkdirs
    }

    val writer = new PrintWriter(bfPath.toString(), "UTF-8")
    writer.println(content)
    writer.flush()
    writer.close()

    logger.debug("Successfully render batch to " + bfPath.toString)
  }

  private def generateOutputBatchFileName(id: Int): String = {
    val dir = RenderConfiguration.outputBatchFileLocation.getOrElse("outputbatchdir")
    val filename = "bj" + "_" + GlobalBatchJobInfo.getTaskId + "_" + id

    if (!dir.endsWith(File.separator)) {
      return dir + File.separator + filename
    } else
      return dir + filename
  }
}