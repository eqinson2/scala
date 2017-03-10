package com.ericsson.scala.XML

import scala.xml.Attribute
import scala.xml.Elem
import scala.xml.Node
import scala.xml.Null
import scala.xml.XML
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer

object NoAlt extends App {
  //val xml1 = <c><a><b attr1="100" attr2="50"></b></a><c>
  val root = XML.loadFile("xml/myXHTML.xml")

  // val rule1 = new RewriteRule {
  //    override def transform(n: Node) = n match {
  //      case e @ <b>{ _* }</b> =>
  //        e.asInstanceOf[Elem] % Attribute(null, "attr2", "100", Null)
  //      case _ => n
  //    }
  //  }

  val rule1 = new RewriteRule {
    override def transform(n: Node) = n match {
      case e @ <img>{ _* }</img> if (e.attribute("alt").isEmpty) =>
        e.asInstanceOf[Elem] % Attribute(null, "alt", "TODO", Null)
      case _ => n
    }
  }

  val xml2 = new RuleTransformer(rule1).transform(root)
  println(xml2(0))
}