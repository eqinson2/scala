package com.ericsson.scala.XML

import scala.xml.XML
import scala.xml.NodeSeq

object xhtml extends App {
  val root = XML.loadFile("xml/myXHTML.xml")
  println(root)
  for (n <- root \\ "img") {
    if (n.attributes.get("alt").isEmpty)
      println(n)
  }

  val imgs: NodeSeq = root \\ "img"  
  // img \ "@alt " 's type is scala.xml.NodeSeq  
  imgs.foreach { x => println(x \ "@src" text) }  
  

  for (n <- root \\ "a") {
    val url = n \ "@href"
    if (url.length > 0) println(url(0))
  }
}