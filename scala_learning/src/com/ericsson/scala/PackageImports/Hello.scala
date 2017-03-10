package com.ericsson.scala.PackageImports

object Hello extends App {
  import java.lang.System
  println(System.getProperty("user.name"))

  import java.lang.System.console
  import java.lang.System.out
  import java.lang.System.err
  import java.lang.System.in

  val co = System.console();
  val passwd = co.readLine()
  if (passwd != null && passwd.equals("secrect"))
    err.println("not secrect!!!")
  else out.println("Hello")
}