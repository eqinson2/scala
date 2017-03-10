package com.ericsson.scala.Parser

import scala.util.parsing.combinator._

class NumSeqParser extends RegexParsers {
  val number = "[-]*[0-9]+".r
  var list: List[Int] = List[Int]()

  def expr: Parser[List[Int]] = numeric ~ opt("," ~ expr) ^^ {
    case n ~ None          => n :: list
    case n ~ Some("," ~ e) => n :: e
  }

  def numeric: Parser[Int] = number ^^ { _.toInt } | failure("invalid number format")
}

object NumSeqParser extends App {
  val parser = new NumSeqParser
  val result = parser.parseAll(parser.expr, "212,-4,3,5,6")
  if (result.successful) println(result.get)
}