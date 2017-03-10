package com.ericsson.scala.Parser

import scala.util.parsing.combinator._
import scala.io.Source
import scala.xml.Elem
import java.io.File

abstract class XML
case class Element(name: String, attributes: Map[String, String], contents: Seq[XML]) extends XML
case class Letter(c: Char) extends XML
case class CDATA(text: String) extends XML
case class CharEntity(c: Char) extends XML
case class Entity(c: Char) extends XML
case class Comment(text: String) extends XML

class SimpleXMLParser extends RegexParsers {
  val name = "[_a-zA-Z0-9]+".r
  val value = """'(.*?)'|"(.*?)"""".r
  val literal = "[^&<>]+".r

  def attribute = name ~ "=" ~ value ^^ {
    case x ~ "=" ~ y => (x, y)
  }

  def startTag = "<" ~> name ~ rep(attribute) <~ ">" ^^ {
    case x ~ y => (x, Map() ++ y)
  }

  //  def startTag = "<" ~> name into { id =>
  //    rep(attribute) <~ ">" ^^ {
  //      case y => (id, Map() ++ y)
  //    }
  //  }

  def emptyTag = "<" ~> name ~ rep(attribute) <~ "/>" ^^ {
    case x ~ y => Element(x, Map() ++ y, Seq())
  }

  def endTag = "</" ~> name <~ ">"

  def element: Parser[Element] = startTag into { begin =>
    rep(content) ~ endTag ^^ {
      //case (name, attribs) ~ contents ~ _ => Element(name, attribs, contents)
      case contents ~ end if (begin._1 == end) => Element(begin._1, begin._2, contents)
      case _ ~ end                             => throw new Exception("Xml tag mismatch: begin tag is " + begin._1 + " while end tag is " + end)
    }
  }

  def content = (
    element
    | literal ^^ { s: String => Letter(s.charAt(0)) }
    | cdata
    | charEntity)

  def charEntity = "&#" ~> "[0-9a-fA-F]+".r <~ ";" ^^ { hex: String => CharEntity(Integer.parseInt(hex, 16).toChar) }

  def cdata = "<![CDATA[" ~> """[^\]]*""".r <~ "]]>" ^^ { CDATA(_) }
}

object XMLParser extends App {
  val parser = new SimpleXMLParser
  val xml = Source.fromFile("xml/testParser.xml").mkString.replaceAll("\r\n", "")
  println(xml)
  val result = parser.parseAll(parser.content, xml)
  println(result.get)
}