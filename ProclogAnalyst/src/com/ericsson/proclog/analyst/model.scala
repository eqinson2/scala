package com.ericsson.proclog.analyst

class BasicInfo(val logType: String, val operation: String, val starttime: String, val duration: String, val indentLevel: Int) {
  override def toString = {
    val indent = new StringBuilder()
    for (i <- 1 to indentLevel) indent.append("	")
    indent.toString + String.format("%s-->%s	|	%s	%s", logType, starttime, duration, operation)
  }
}

class CSOEntry(val logid: String, val northbound: NorthBound, val result: String) extends Ordered[CSOEntry] {
  private var mca21: List[MCA21Entry] = List()

  def addMCA21(entry: MCA21Entry) = mca21 = mca21 :+ entry

  override def toString = {
    val sb = new StringBuilder()
    sb.append(logid + "	" + result + "\n")
    sb.append(northbound.operation + "-->" + northbound.starttime + "	|	" + northbound.duration + "\n")
    mca21.foreach { sb.append(_) }
    sb.append("\n\n\n\n").toString
  }

  override def compare(cso: CSOEntry) = {
    if (this eq cso) 0
    else if (northbound.duration.toFloat < cso.northbound.duration.toFloat) 1
    else -1
  }
}

class MCA21Entry(override val logType: String, override val operation: String, override val starttime: String,
                 override val duration: String, val detailedRequest: String, override val indentLevel: Int)
    extends BasicInfo(logType, operation, starttime, duration, indentLevel) {
  private var southbounds: List[SouthBound] = List()

  def addSouthBound(sb: SouthBound) = southbounds = southbounds :+ sb

  override def toString = {
    val sb = new StringBuilder
    val indent = new StringBuilder
    for (i <- 1 to indentLevel) indent.append("	")
    sb.append(indent + String.format("%s-->%s	|	%s-->%s		%s", logType, starttime, duration, operation, detailedRequest) + "\n")
    southbounds.foreach { s => sb.append(indent + s.toString + "\n") }
    sb.toString
  }
}

class NorthBound(override val logType: String, override val operation: String, override val starttime: String,
                 override val duration: String, override val indentLevel: Int)
    extends BasicInfo(logType, operation, starttime, duration, indentLevel)

class SouthBound(override val logType: String, override val operation: String, override val starttime: String,
                 override val duration: String, val detailedRequest: String, override val indentLevel: Int)
    extends BasicInfo(logType, operation, starttime, duration, indentLevel) {

  override def toString = {
    val indent = new StringBuilder
    for (i <- 1 to indentLevel) indent.append("	")
    if (duration.toFloat <= 0.005)
      indent.toString() + String.format("%6s-->%s	|	%s				%s	%s", logType, starttime, duration, operation, detailedRequest)
    else
      indent.toString() + String.format("%6s-->%s	|	%s *			%s	%s", logType, starttime, duration, operation, detailedRequest)
  }
}
