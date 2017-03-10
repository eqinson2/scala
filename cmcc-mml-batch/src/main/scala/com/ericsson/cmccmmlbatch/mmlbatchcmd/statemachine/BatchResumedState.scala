package com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine

class BatchResumedState(val machine: TransitMachine) extends IBatchCommandState {
  override def canCreate = false

  override def canCancel = false

  override def canPause = true

  override def canResume = false

  override def create = synchronized {
    throw new BatchStateException(1004)
  }

  override def cancel = synchronized {
    throw new BatchStateException(3002)
  }

  override def pause = synchronized {
    machine.setState(machine.pausedState)
  }

  override def resume = synchronized {
    throw new BatchStateException(4002)
  }
}