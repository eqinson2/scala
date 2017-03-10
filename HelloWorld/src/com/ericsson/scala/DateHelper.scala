package com.ericsson.scala

import java.util.Date
import java.util.Calendar

class DateHelper(number: Int) {
  def days(when: String): Date = {
    var date = Calendar.getInstance()
    when match {
      case "ago"      => date.add(Calendar.DAY_OF_MONTH, -number)
      case "from_now" => date.add(Calendar.DAY_OF_MONTH, number)
      case _          => date
    }
    date.getTime
  }
}

object DateHelper extends App {
  val ago = "ago"
  val from_now = "from_now"

  implicit def convertInt2DateHelper(number: Int) = new DateHelper(number)

  val past = 2 days ago
  val appointment = 5 days from_now
  println(past)
  println(appointment)
}