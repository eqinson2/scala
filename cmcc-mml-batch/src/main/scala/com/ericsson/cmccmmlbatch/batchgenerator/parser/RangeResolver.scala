package com.ericsson.cmccmmlbatch.batchgenerator.parser

import com.ericsson.cmccmmlbatch.batchgenerator.parser.BatchJobParser._

object RangeResolver {
  val MAX_RANGE_WIDTH = 6

  def isValidRange(s: String) = isRange(s) && s.length >= SIZE_OF_NORMAL_SUB - MAX_RANGE_WIDTH

  def isRange(s: String) = s.length < SIZE_OF_NORMAL_SUB && s.length() > 0
}

class RangeResolver(s: String) {
  private val prefix: String = s
  private var end_val: Int = _
  private var value: Int = 0
  private var rangeTooLong = false
  private var rangeLength: Int = _

  init()

  def init(): Unit = {
    if (!RangeResolver.isValidRange(s)) {
      rangeTooLong = true
      return
    }

    val sb_right = new StringBuilder
    (0 until SIZE_OF_NORMAL_SUB - prefix.length).foldLeft(sb_right)((sb, _) => sb.append("9"))

    end_val = sb_right.toString.toInt
    rangeLength = SIZE_OF_NORMAL_SUB - prefix.length
  }

  def reachHigherBound = value > end_val

  def getNextValue = {
    val sb = new StringBuilder
    (0 until getNumberOfZeroPadding(value)).foldLeft(sb)((s, _) => s.append("0"))

    val prefix_sb = new StringBuilder(prefix)
    val result = prefix_sb.append(sb).append(value.toString).toString
    value += 1
    result
  }

  private def getNumberOfZeroPadding(x: Int) = rangeLength - x.toString.length

  def isRangeTooLong = rangeTooLong
}