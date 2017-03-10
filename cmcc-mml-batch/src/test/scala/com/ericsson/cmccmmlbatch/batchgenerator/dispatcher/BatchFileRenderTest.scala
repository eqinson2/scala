package com.ericsson.cmccmmlbatch.batchgenerator.dispatcher

import java.util.ArrayList

import com.ericsson.cmccmmlbatch.batchgenerator.render.BatchFileRender
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

object BatchFileRenderTest

class BatchFileRenderTest extends FlatSpec with Matchers with BeforeAndAfterAll {
  "BatchFileRender" should "generate batch file correctly" in {
    val ids = new ArrayList[String]
    ids.add("1")
    ids.add("2")
    ids.add("3")
    val render = new BatchFileRender(1)
    render.render(ids)
  }
}