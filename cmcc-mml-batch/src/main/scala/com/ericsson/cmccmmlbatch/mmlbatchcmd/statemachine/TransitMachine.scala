package com.ericsson.cmccmmlbatch.mmlbatchcmd.statemachine

trait IBatchCommandState {
  def create(): Unit

  def cancel(): Unit

  def pause(): Unit

  def resume(): Unit

  def canCreate: Boolean

  def canCancel: Boolean

  def canPause: Boolean

  def canResume: Boolean
}

object TransitMachine {
  var instance: Option[TransitMachine] = None

  def apply() = synchronized {
    if (instance.isEmpty)
      instance = Some(new TransitMachine)
    instance.get
  }
}

class TransitMachine {
  var state: IBatchCommandState = null

  var initialState = new BatchInitialState(this)
  var createdState = new BatchCreatedState(this)
  var pausedState = new BatchPausedState(this)
  var resumedState = new BatchResumedState(this)

  reset()

  def getState = state

  def setState(state: IBatchCommandState) = this.state = state

  def canCreate = state.canCreate

  def canCancel = state.canCancel

  def canPause = state.canPause

  def canResume = state.canResume

  def create() = state.create()

  def cancel() = state.cancel()

  def pause() = state.pause()

  def resume() = state.resume()

  def reset() = this.state = initialState
}