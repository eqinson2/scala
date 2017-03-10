package com.ericsson.scala.Parser

import scala.util.parsing.combinator._

import java.util.Date
/*
 * YYYY = four-digit year
     MM   = two-digit month (01=January, etc.)
     DD   = two-digit day of month (01 through 31)
     hh   = two digits of hour (00 through 23) (am/pm NOT allowed)
     mm   = two digits of minute (00 through 59)
     ss   = two digits of second (00 through 59)
     s    = one or more digits representing a decimal fraction of a second
     example:1994-11-05T08:15:30
 */
class ISO8601DateParser extends RegexParsers {
  val number = "[0-9]+".r
  val dateValue = new Date

  def expr: Parser[Date] = year ~ "-" ~ month ~ "-" ~ date ~ opt("T" ~ hms) ^^ {
    case yyyy ~ "-" ~ mm ~ "-" ~ dd ~ None => {
      dateValue.setYear(yyyy); dateValue.setMonth(mm); dateValue.setDate(dd);
      dateValue
    }
    case yyyy ~ "-" ~ mm ~ "-" ~ dd ~ Some(hms) => {
      dateValue.setYear(yyyy); dateValue.setMonth(mm); dateValue.setDate(dd);
      dateValue
    }
  }

  def hms: Parser[Date] = hour ~ ":" ~ min ~ ":" ~ second ^^ {
    case hh ~ ":" ~ mi ~ ":" ~ sec => {
      dateValue.setHours(hh); dateValue.setMinutes(mi); dateValue.setSeconds(sec);
      dateValue
    }
  }

  def year: Parser[Int] = numeric
  def month: Parser[Int] = numeric
  def date: Parser[Int] = numeric
  def hour: Parser[Int] = numeric
  def min: Parser[Int] = numeric
  def second: Parser[Int] = numeric

  def numeric: Parser[Int] = number ^^ { _.toInt } | failure("invalid number format")
}

object ISO8601DateParser extends App {
  val parser = new ISO8601DateParser
  val result = parser.parseAll(parser.expr, "2016-07-29T08:15:30")
  if (result.successful)
    print(result.get.getYear, result.get.getMonth, result.get.getDate, result.get.getHours, result.get.getMinutes, result.get.getSeconds)
}