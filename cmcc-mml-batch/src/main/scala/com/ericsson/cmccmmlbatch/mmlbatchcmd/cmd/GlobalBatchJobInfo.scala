package com.ericsson.cmccmmlbatch.mmlbatchcmd.cmd

import java.text.SimpleDateFormat
import java.util.Date

object GlobalBatchJobInfo {
  private var inputBatchFile: String = null
  private var taskId: String = null

  object State extends Enumeration {
    type State = Value
    val NOTSTART, RUNNING, FINISH, PAUSED = Value
  }

  @volatile
  private var dispatcherState = State.NOTSTART
  @volatile
  private var reporterState = State.NOTSTART

  def getInputBatchFile = inputBatchFile

  def setInputBatchFile(file: String) = this.inputBatchFile = file

  def generateTaskId() = taskId = new SimpleDateFormat("yyyyMMddHHmm").format(new Date)

  def getTaskId = taskId

  def getDispatcherState = dispatcherState

  import State._

  def setDispatcherState(state: State) = this.dispatcherState = state

  def getReporterState = reporterState

  def setReporterState(state: State) = this.reporterState = state

  def resetState() = {
    dispatcherState = State.NOTSTART
    reporterState = State.NOTSTART
  }
}