package com.ericsson.scala.Class

class Time {
  var hours: Int = 0
  var min: Int = 0

  def this(hours: Int, min: Int) {
    this
    if (hours <= 24 && hours > 0) this.hours = hours
    if (min <= 60 && min >= 0) this.min = min
  }

  def before(other: Time) = this.hours < other.hours || this.hours == other.hours && this.min < other.min
}

class Time2() {
  var elapsedMin: Int = 0

  def this(hours: Int, min: Int) {
    this
    elapsedMin = hours * 24 + min
  }

  def before(other: Time2) = elapsedMin < other.elapsedMin
}