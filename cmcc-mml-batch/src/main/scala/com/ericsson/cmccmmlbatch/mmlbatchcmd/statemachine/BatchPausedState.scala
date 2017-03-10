package com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine

class BatchPausedState(val machine: TransitMachine) extends IBatchCommandState {
  override def canCreate = false

  override def canCancel = true

  override def canPause = false

  override def canResume = true

  override def create = synchronized {
    throw new BatchStateException(1003)
  }

  override def cancel = synchronized {
    machine.setState(machine.initialState)
  }

  override def pause = synchronized {
    throw new BatchStateException(2001)
  }

  override def resume = synchronized {
    machine.setState(machine.resumedState)
  }
}