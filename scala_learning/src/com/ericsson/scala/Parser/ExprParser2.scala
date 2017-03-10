package com.ericsson.scala.Parser

import scala.util.parsing.combinator._

class ExprParser2 extends RegexParsers {
  val number = "[0-9]+".r
  def expr: Parser[Double] = term ~ opt(("+" | "-") ~ expr) ^^ {
    case t ~ None          => t
    case t ~ Some("+" ~ e) => t + e
    case t ~ Some("-" ~ e) => t - e
  }
  def term: Parser[Double] = factor ~ rep(("*" | "/" | "%") ~ factor) ^^ {
    case f ~ r =>
      var result = f
      r.foreach {
        case "*" ~ f0 => result *= f0
        case "/" ~ f0 => result /= f0
        case "%" ~ f0 => result %= f0
      }
      result
  }
  def factor: Parser[Double] = number ^^ { _.toDouble } | "(" ~ expr ~ ")" ^^ {
    case _ ~ e ~ _ => e
  }
}

object ExprParser2 extends App {
  val parser = new ExprParser2
  val result = parser.parseAll(parser.expr, "3-5*6+24/4%2+1")
  if (result.successful) println(result.get)
}