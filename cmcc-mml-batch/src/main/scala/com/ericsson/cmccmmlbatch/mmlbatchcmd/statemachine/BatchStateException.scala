package com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine

class BatchStateException extends Exception {
  private val serialVersionUID = 1L
  var errorcode: Int = 0
  private var errorMap = Map[Int, String]()

  def this(errorcode: Int) {
    this()
    this.errorcode = errorcode;
  }

  errorMap = errorMap + (1000 -> "bhLogWatcher fail to start, abort")
  errorMap = errorMap + (1001 -> "Batch handler in faulty state, abort")
  errorMap = errorMap + (1007 -> "Input batch file invalid format, abort")

  // initial->pause, cancel, resume
  errorMap = errorMap + (2002 -> "Task not running, can not do pause")
  errorMap = errorMap + (3003 -> "Task not running, can not do cancel")
  errorMap = errorMap + (4003 -> "Task not running, can not do resume")

  // created->create/cancel/resume
  errorMap = errorMap + (1002 -> "Task already created, can not create again")
  errorMap = errorMap + (3001 -> "Task created, can not cancel it while running")
  errorMap = errorMap + (4001 -> "Task created, can not resume it while running")

  // paused->create/pause
  errorMap = errorMap + (1003 -> "Task paused, can not create new one before cancel old task")
  errorMap = errorMap + (2001 -> "Task already paused, can not pause again")

  // resumed->create/cancel/resume
  errorMap = errorMap + (1004 -> "Cannot create task in resumed state")
  errorMap = errorMap + (3002 -> "Cannot cancel task in resumed state")
  errorMap = errorMap + (4002 -> "Cannot resume task in resumed state")

  // miss batch file
  errorMap = errorMap + (1005 -> "File not exist, please put file on server first")

  // miss batch file
  errorMap = errorMap + (1006 -> "Still generating report, can not create new task now")

  def getErrorCode = errorcode

  def getErrorMessage = errorMap.get(errorcode)
}