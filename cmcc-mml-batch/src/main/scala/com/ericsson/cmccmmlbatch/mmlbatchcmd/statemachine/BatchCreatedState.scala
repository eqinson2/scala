package com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine


class BatchCreatedState(val machine: TransitMachine) extends IBatchCommandState {
  override def canCreate = false

  override def canCancel = false

  override def canPause = true

  override def canResume = false

  override def create = synchronized {
    throw new BatchStateException(1002)
  }

  override def cancel = synchronized {
    throw new BatchStateException(3001)
  }

  override def pause = synchronized {
    machine.setState(machine.pausedState)
  }

  override def resume = synchronized {
    throw new BatchStateException(4001)
  }
}