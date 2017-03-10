package com.ericsson.scala

import java.net.URL

import scala.sys.process._

/**
  * Created by eqinson on 2016/9/27.
  */
object process extends App {
  //"ls -al *" !

  val result = "ls -al *" #| "grep eqinson" !!

  //println(result)
  val result2 = "grep meaningful" #< new URL("http://horstman.com/index.html") #| "grep title" !!

  print(result2)
}
