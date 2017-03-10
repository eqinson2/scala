package com.ericsson.cmccmmlbatch.batchgenerator.parser

import java.io.{BufferedReader, File, FileNotFoundException, FileReader, IOException}

import com.ericsson.cmccmmlbatch.config.BatchFileRenderConfigProvider
import org.apache.logging.log4j.LogManager

import scala.util.control.Breaks._

object BatchJobParser {
  val SIZE_OF_NORMAL_SUB = 11
}

class BatchJobParser(val filename: String) extends BatchFileRenderConfigProvider {
  private val logger = LogManager.getLogger(BatchJobParser)

  private val NORMAL_SUBSCRIBER_REGEXP = "(\\d+)".r

  private var reader: Option[BufferedReader] = None
  private var rr: RangeResolver = null
  private var isInRange = false

  init()

  def close() = try {
    reader.get.close()
  } catch {
    case _: Throwable =>
  }

  def getNextSubscriber: String = {
    var line = ""
    breakable {
      do {
        if (!isInRange) {
          try {
            breakable {
              while (true) {
                line = reader.get.readLine
                if (line == null) break

                if (!line.trim.equals("")) {
                  if (isLegalSubscriber(line) && !RangeResolver.isRange(line)) {
                    isInRange = false
                    return line.trim
                  } else if (RangeResolver.isRange(line) && RangeResolver.isValidRange(line)) {
                    logger.debug("Meet range: " + line)
                    rr = new RangeResolver(line)
                    if (!rr.isRangeTooLong) {
                      isInRange = true
                      break
                    } else {
                      logger.error("Ignore range! Range too long: " + line)
                      isInRange = false
                    }
                  } else {
                    logger.error("Ignore ! Invalid subscriber or range: " + line)
                    isInRange = false
                  }
                }
              }
            }
          } catch {
            case e: IOException => logger.error("read batch file error..."); throw e
          }
          if (line == null)
            break
        }

        if (isInRange && !rr.reachHigherBound) return rr.getNextValue
        else isInRange = false
      } while (!isInRange)
    }
    null
  }

  private def isLegalSubscriber(s: String) = {
    s match {
      case NORMAL_SUBSCRIBER_REGEXP(_) if s.length == BatchJobParser.SIZE_OF_NORMAL_SUB => true
      case _ => false
    }
  }

  def validateFormat: Int = {
    if (reader.isEmpty) {
      logger.error("no such input batch file")
      return -1
    }

    var line: String = ""
    var c = 0
    try {
      breakable {
        while (true) {
          line = reader.get.readLine
          if (line == null) break

          if (!line.trim.equals("")) {
            if (isLegalSubscriber(line) && !RangeResolver.isRange(line))
              c += 1
            else if (RangeResolver.isRange(line) && RangeResolver.isValidRange(line)) {
              val thisLine = (0 until BatchJobParser.SIZE_OF_NORMAL_SUB - line.length).foldLeft(1)((carry, _) => carry * 10)
              logger.debug("range line: {} contains {} numbers", line, thisLine)
              c += thisLine
            } else {
              logger.info("inputfile invalid format")
              return -1
            }
          }
        }
      }
    } catch {
      case e: IOException =>
        logger.info("inputfile invalid format")
        return -1
    }
    logger.info("sub count for validate format: {}", c)

    if (c == 0) {
      logger.info("inputfile invalid format, empty input file")
      -1
    } else if (c > RenderConfiguration.maxSubscriberPerInputFile.get) {
      logger.info("inputfile invalid format, exceed sub count upperlimit")
      -1
    } else
      c
  }

  private def init() = {
    val fn = locateFile(filename)
    logger.info("Parse batch file: " + fn)
    try
      reader = Some(new BufferedReader(new FileReader(new File(fn))))
    catch {
      case e: FileNotFoundException => logger.error("can not open file {}", fn)
    }
  }

  private def locateFile(filename: String) = {
    val dir = RenderConfiguration.inputBatchFileLocation.getOrElse("inputbatchdir")
    if (!dir.endsWith(File.separator)) dir + File.separator + filename
    else dir + filename
  }
}