package com.ericsson.scala.ClassExtends

class Person2(val name: String) {
  override def toString = getClass.getName + "[name=" + name + "]"
}

class SecretAgent(codename: String) extends Person2(codename) {
  //code name here is a private[this] val
  override val name = "secrect"
  override val toString = "secrect" + codename
}


/*
C:\maworkspace\scala\scala_learning\bin\com\ericsson\scala\chapter8
λ javap Person2.class
Compiled from "Person2.scala"
public class com.ericsson.scala.chapter8.Person2 {
  public java.lang.String name();
  public java.lang.String toString();
  public com.ericsson.scala.chapter8.Person2(java.lang.String);
}

C:\maworkspace\scala\scala_learning\bin\com\ericsson\scala\chapter8
λ javap SecretAgent.class
Compiled from "Person2.scala"
public class com.ericsson.scala.chapter8.SecretAgent extends com.ericsson.scala.chapter8.Person2 {
  public java.lang.String name();
  public java.lang.String toString();
  public com.ericsson.scala.chapter8.SecretAgent(java.lang.String);
}
*/