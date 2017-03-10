package com.ericsson.cmccmmlbatch.utils

import java.io.{File, IOException}

import com.ericsson.cmccmmlbatch.config.{BatchFileRenderConfigProvider, BatchHandlerConfigProvider}
import org.apache.logging.log4j.LogManager

object IOUtils extends BatchFileRenderConfigProvider with BatchHandlerConfigProvider {
  private val logger = LogManager.getLogger(IOUtils)

  def cleanup = {
    logger.info("Delete all files under " + RenderConfiguration.outputBatchFileLocation.get)
    delAllFile(RenderConfiguration.outputBatchFileLocation.get)
    logger.info("Delete all files under " + BHConfiguration.batchJobDir.get)
    delAllFile(BHConfiguration.batchJobDir.get)
    logger.info("Delete all files under " + BHConfiguration.batchLogDir.get)
    delAllFile(BHConfiguration.batchLogDir.get)
  }

  def delAllFile(path: String): Boolean = {
    val dir = new File(path)
    if (!dir.exists) return false
    if (!dir.isDirectory) return false

    def getAllFilesRecursively(f: File): Seq[File] = f.listFiles.filter(!_.isDirectory) ++ f.listFiles.filter(_.isDirectory).flatMap(getAllFilesRecursively)
    def recursivlyeDeleteOutputDir(d: File) = if (d.exists) getAllFilesRecursively(d).foreach(_.delete)

    try {
      recursivlyeDeleteOutputDir(dir)
      return true
    } catch {
      case e: IOException =>
        logger.error("delAllFile exception occur.")
        logger.error(e.getMessage)
        return false
    }
  }
}