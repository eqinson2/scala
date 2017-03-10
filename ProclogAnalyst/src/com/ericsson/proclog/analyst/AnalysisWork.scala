package com.ericsson.proclog.analyst

import scala.collection.immutable.SortedSet
import scala.util.control.Breaks._

object AnalysisWork {
  def apply(text: String) = new AnalysisWork(text)
}

class AnalysisWork(text: String) {
  private[this] val content: Array[String] = text.split("\n")
  private[this] var setOfMCA21: Set[CSOEntry] = SortedSet()
  private[this] type StrArr = Array[String]

  private[this] def isHeaderOfNBIA(fields: StrArr) = fields(0).contains("northbound") && fields(10).contains("MCA21@http://www.chinamobile.com")

  private[this] def isEndOfNBIA(fields: StrArr, id: String) = fields(0).contains("northbound") && !fields(1).contains(id)

  private[this] def isHeaderOfMCA21(fields: StrArr, id: String) = fields(0).contains("MCA21") && fields(1).contains(id)

  private[this] def isHeaderOfSouthBound(fields: StrArr, id: String) = fields(0).contains("southbound") && fields(1).contains(id)

  private[this] def isEndOfSouthBound(fields: StrArr) = fields(0).contains("southbound")

  private[this] def getLogid(fields: StrArr) = fields(1)

  private def groupByLogId() = {
    if (content.length < 2) Unit

    var index = 1
    breakable {
      while (index < content.length) {
        var line = content(index)
        var fields = line.split(",")

        if (isHeaderOfNBIA(fields)) {
          val sb = new StringBuilder()
          val logid = getLogid(fields)
          breakable {
            while (!isEndOfNBIA(fields, logid)) {
              sb.append(line + "\n")

              if (index == content.length - 1) break

              index = index + 1
              line = content(index)
              fields = line.split(",")
            }
          }
          setOfMCA21 = setOfMCA21 + sortoutByLogIdForMCA21(logid.substring(1, logid.length() - 1), sb.toString.split("\n"))
          if (index == content.length - 1)
            break
        } else
          index = index + 1
      }
    }
  }

  def sortoutByLogIdForMCA21(id: String, lines: StrArr) = {
    var fields = lines(0).split(",")
    var result = lines(0).split(",")(6)
    result = result.substring(1, result.length() - 1)

    val cso = new CSOEntry(id, new NorthBound("NBI", formatOper(fields), formatTime(fields), formatDuration(fields), 1), result)

    breakable {
      var index = 0
      while (index < lines.length) {
        var line = lines(index)
        fields = line.split(",")

        if (isHeaderOfMCA21(fields, id)) {
          val sb = new StringBuilder()
          val logid = getLogid(fields)
          var started = false
          breakable {
            while (!started || !isHeaderOfMCA21(fields, logid)) {
              started = true
              sb.append(line + "\n")

              if (index == lines.length - 1) break

              index = index + 1
              line = lines(index)
              fields = line.split(",")
            }
          }
          sortoutByLogIdForSouthBound(sb.toString, id, cso)
          if (index == lines.length - 1)
            break
        } else
          index = index + 1
      }
    }
    cso
  }

  private def sortoutByLogIdForSouthBound(rawMCA21: String, id: String, cso: CSOEntry) {
    val lines = rawMCA21.split("\n")
    val fields = lines(0).split(",")

    val mcs21Req = formatOper(fields).equals("DELETE") match {
      case true =>
        val req = lines(0).split(",")(10)
        req.substring(1, req.length - 1)
      case false => lines(1).substring(1, lines(1).length - 1)
    }

    val mca21 = new MCA21Entry("MCA21", formatOper(fields), formatTime(fields), formatDuration(fields), mcs21Req, 2)
    cso.addMCA21(mca21)

    var index = 0
    breakable {
      while (index < lines.length) {
        var line = lines(index)
        var fields = line.split(",")

        if (isHeaderOfSouthBound(fields, id)) {
          val nso = new SouthBound(getNeName(fields), formatOper(fields), formatTime(fields), formatDuration(fields), formatRequest(line), 3)
          mca21.addSouthBound(nso)

          breakable {
            var started = false
            while (!started || !isEndOfSouthBound(fields)) {
              started = true
              if (index == lines.length - 1)
                break

              index = index + 1
              line = lines(index)
              fields = line.split(",")
            }
          }

          if (index == lines.length - 1)
            break
        } else
          index = index + 1
      }
    }
  }

  def analyze() {
    groupByLogId()
    setOfMCA21.foreach(cso => Utils.write(cso, Utils.prepareFile(cso.northbound.operation)))
  }

  private def formatTime(fields: StrArr) = {
    val starttime = fields(11).split(" ")(1)
    starttime.substring(0, starttime.length - 1)
  }

  private def formatDuration(fields: StrArr) = {
    val duration = fields(12).split(":")(2)
    duration.substring(0, duration.length() - 1)
  }

  private def formatOper(fields: StrArr) = {
    val oper = fields(5)
    oper.substring(1, oper.length() - 1)
  }

  private def formatRequest(line: String) = {
    val fields = line.split("\",\"")
    val req = fields(14)
    // dn: mscId=3848ebd13cb1407689272e9da59f6c17,ou=multiSCs,dc=cmsd
    if (req.contains("dn:")) {
      val pos = req.indexOf("ou")
      req.substring(0, pos - 1)
    } else req
  }

  private def getNeName(fields: StrArr) = {
    if (fields(9).contains("LDAP")) "CUDB"
    else if (fields(9).contains("TELNET")) "HLRFE"
    else "Others"
  }
}