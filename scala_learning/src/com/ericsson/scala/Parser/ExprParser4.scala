package com.ericsson.scala.Parser

import scala.util.parsing.combinator._

abstract class Expr
case class Number(value: Int) extends Expr
case class Operator(op: String, left: Expr, right: Expr) extends Expr

class ExprParser4 extends RegexParsers {
  val number = "[0-9]+".r

  import Int2NumberConversion._

  def expr: Parser[Expr] = term ~ rep( //左结合
    ("+" | "-") ~ term ^^ {
      case "+" ~ t => Operator("+", 0, t)
      case "-" ~ t => Operator("-", 0, t)
    }) ^^ {
      case t ~ r => r.foldLeft(Operator("+", 0, t))(Operator("+", _, _))
    }

  def term: Parser[Expr] = factor ~ opt("*" ~> term) ^^ { //左结合
    case f ~ None    => f
    case f ~ Some(t) => Operator("*", f, t)
  }

  def factor: Parser[Expr] = number ^^ { x: String => Number(x.toInt) } | "(" ~> expr <~ ")"
}

object Int2NumberConversion {
  implicit def Int2Number(value: Int): Expr = Number(value)
}

object ExprParser4 extends App {
  val parser = new ExprParser4

  val result1 = parser.parseAll(parser.expr, "1-4*2-2*3")
  if (result1.successful) println(result1.get)

  val result2 = parser.parseAll(parser.expr, "3-4-5+6-7+8-9")
  if (result2.successful) println(result2.get)

  def eval(v: Expr): Int = {
    v match {
      case n: Number           => n.value
      case Operator("+", l, r) => eval(l) + eval(r)
      case Operator("-", l, r) => eval(l) - eval(r)
      case Operator("*", l, r) => eval(l) * eval(r)
    }
  }

  println(eval(result1.get))
  println(eval(result2.get))
}