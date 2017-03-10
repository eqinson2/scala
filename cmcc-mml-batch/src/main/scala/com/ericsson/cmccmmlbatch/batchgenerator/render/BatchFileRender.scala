package com.ericsson.cmccmmlbatch.batchgenerator.render

import java.io.IOException

import com.ericsson.cmccmmlbatch.config.BatchFileRenderConfigProvider
import org.apache.logging.log4j.LogManager
import org.stringtemplate.v4.{STGroupFile, StringRenderer}


object BatchFileRender

class BatchFileRender(val id: Int) extends BatchFileRenderConfigProvider {
  private val logger = LogManager.getLogger(BatchFileRender)

  def render(subIdCollection: java.util.List[String]) {
    val endPoint = RenderConfiguration.batchProvEndPoint.get
    val user = RenderConfiguration.pgUserName.get
    val passWord = RenderConfiguration.pgPasswd.get

    val model = new BatchFileModel(endPoint, user, passWord, "Get", subIdCollection)
    val group = new STGroupFile("templates/BatchFile.stg", '$', '$')
    group.registerRenderer(classOf[String], new StringRenderer)
    val st = group.getInstanceOf("Cai3gBatch")
    st.add("bf", model)

    try
      BatchFileWriterUtils().writeToFile(st.render, id)
    catch {
      case e: IOException => logger.error("BatchFileWriterUtils.writeToFile() failed:" + e.getMessage)
    }
  }
}