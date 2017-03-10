package com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine


class BatchInitialState(val machine: TransitMachine) extends IBatchCommandState {
  override def canCreate = true

  override def canCancel = false

  override def canPause = false

  override def canResume = false

  override def create = synchronized {
    machine.setState(machine.createdState)
  }

  override def cancel = synchronized {
    throw new BatchStateException(3003)
  }

  override def pause = synchronized {
    throw new BatchStateException(2002)
  }

  override def resume = synchronized {
    throw new BatchStateException(4003)
  }
}