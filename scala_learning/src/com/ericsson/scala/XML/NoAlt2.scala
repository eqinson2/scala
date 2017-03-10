package com.ericsson.scala.XML

import scala.xml.Attribute
import scala.xml.Elem
import scala.xml.Node
import scala.xml.Null
import scala.xml.XML
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer
import scala.xml.dtd.PublicID
import scala.xml.dtd.DocType

object NoAlt2 extends App {
  val root = XML.loadFile("xml/myXHTML.xml")
  val rule1 = new RewriteRule {
    override def transform(n: Node) = n match {
      case e @ <img>{ _* }</img> if (e.attribute("alt").isEmpty) =>
        e.asInstanceOf[Elem] % Attribute(null, "alt", "TODO", Null)
      case _ => n
    }
  }

  val xml2 = new RuleTransformer(rule1).transform(root)
  XML.save("xml/myXHTML2.xml", xml2(0), enc = "UTF-8", xmlDecl = true, doctype = DocType("html",
    PublicID("-//W3C//DTD XHTML 1.0 Strict//EN",
      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"),
    Nil))
}