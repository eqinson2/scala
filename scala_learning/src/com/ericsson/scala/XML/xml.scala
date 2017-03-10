package com.ericsson.scala.XML

import scala.xml.Text

object xml extends App {
  val xv = <fred/><fred2/>(1)
  println(xv)

  val xv2 = <ul>
              <li>open bracket: [</li>
              <li>close bracket: ]</li>
              <li>open brace: {{</li>
              <li>close brace: }}</li>
            </ul>
  println(xv2)

  val xv3 = <li>Fred</li> match { case <li>{ Text(t) }</li> => t case _ => }
  val xv4 = <li>{ "Fred" }</li> match { case <li>{ Text(t) }</li> => t case _ => }
  val xv5 = <li>{ Text("Fred") }</li> match { case <li>{ Text(t) }</li> => t case _ => }
  println("xv3:" + xv3)
  println("xv4:" + xv4)
  println("xv5:" + xv5)
}