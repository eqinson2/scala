package com.ericsson.scala.Parser

import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

class OnesPacketParser extends RegexParsers with PackratParsers {
  lazy val ones: PackratParser[Any] = ones ~ "1" | "1"

  def parseAll[T](p: Parser[T], input: String) =
    phrase(p)(new PackratReader(new CharSequenceReader(input)))
}

object OnesPacketParser extends App {
  val parser = new OnesPacketParser
  println(parser.parseAll(parser.ones, "11111111111111111111111"))
}