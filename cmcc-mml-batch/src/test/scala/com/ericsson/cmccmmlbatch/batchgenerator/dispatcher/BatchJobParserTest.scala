package com.ericsson.cmccmmlbatch.batchgenerator.dispatcher

import java.io.IOException

import com.ericsson.cmccmmlbatch.batchgenerator.parser.BatchJobParser
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.util.control.Breaks._

class BatchJobParserTest extends FlatSpec with Matchers with BeforeAndAfterAll {
  override def beforeAll() {
  }

  "BatchJobParser" should "parse all number and number range in input batch file case1" in {
    val p = new BatchJobParser("test_batch1.txt")
    var number = 0
    var n = ""
    try {
      breakable {
        while (true) {
          val n = p.getNextSubscriber
          if (n == null) break
          number += 1
        }
      }
      number should equal(212)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  "BatchJobParser" should "parse all number and number range in input batch file case2" in {
    val p = new BatchJobParser("test_batch3.txt")
    var number = 0
    var n = ""
    try {
      breakable {
        while (true) {
          val n = p.getNextSubscriber
          if (n == null) break
          number += 1
        }
      }
      number should equal(10000)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  "BatchJobParser" should "parse all number and number range in input batch file case3" in {
    val p = new BatchJobParser("test_batch6.txt")
    var number = 0
    var n = ""
    try {
      breakable {
        while (true) {
          val n = p.getNextSubscriber
          if (n == null) break
          number += 1
        }
      }
      number should equal(0)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  "BatchJobParser" should "parse all number and number range in input batch file case4" in {
    val p = new BatchJobParser("test_batch7.txt")
    var number = 0
    var n = ""
    try {
      breakable {
        while (true) {
          val n = p.getNextSubscriber
          if (n == null) break
          number += 1
        }
      }
      number should equal(300000)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  "Input batchfile format" should "be valid" in {
    val p1 = new BatchJobParser("validate.txt");
    p1.validateFormat should equal(-1)

    val p2 = new BatchJobParser("validate1.txt");
    p2.validateFormat should equal(-1)

    val p3 = new BatchJobParser("test_batch_too_long_range.txt");
    p3.validateFormat should equal(-1)

    val p4 = new BatchJobParser("test_batch_with_illegal_char.txt");
    p4.validateFormat should equal(-1)
  }
}