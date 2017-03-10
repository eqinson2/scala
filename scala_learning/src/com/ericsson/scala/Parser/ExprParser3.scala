package com.ericsson.scala.Parser

import scala.util.parsing.combinator._

class ExprParser3 extends RegexParsers {
  val number = "[0-9]+".r

  def expr: Parser[Int] = term ~ rep(
    ("+" | "-") ~ term ^^ {
      case "+" ~ t => t
      case "-" ~ t => -t
    }) ^^ { case t ~ r => t + r.sum }

  def term: Parser[Int] = pow ~ rep("*" ~> pow) ^^ {//左结合
    case p ~ r => p * r.product
  }

  def pow: Parser[Int] = factor ~ opt("^" ~ pow) ^^ {//右结合
    case f ~ None          => f
    case f ~ Some("^" ~ p) => Math.pow(f, p).toInt
  }

  def factor: Parser[Int] = log(number)("number") ^^ { _.toInt } | "(" ~> expr <~ ")"
}

object ExprParser3 extends App {
  val parser = new ExprParser3
  val result = parser.parseAll(parser.expr, "1-4^2^3-2*3")
  if (result.successful) println(result.get)
}