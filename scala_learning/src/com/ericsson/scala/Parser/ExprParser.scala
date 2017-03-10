package com.ericsson.scala.Parser

import scala.util.parsing.combinator._

class ExprParser extends RegexParsers {
  val number = "[0-9]+".r
  def expr: Parser[Any] = term ~ opt(("+" | "-") ~ expr)
  def term: Parser[Any] = pow ~ rep(("*" | "/" | "%") ~ pow)
  def pow: Parser[Any] = factor ~ rep("^" ~ factor)
  def factor: Parser[Any] = number | "(" ~ expr ~ ")"
}

object ExprParser extends App {
  val parser = new ExprParser
  val result = parser.parseAll(parser.expr, "3-4*5^2")
  if (result.successful) println(result.get)
}

//((3~List())~Some(  (-~((4~  List(  (*~5)  )  )~None)))     )

