package com.ericsson.scala.Class

class Person(val name: String) {
  private var privateAge = 0

  def age = privateAge

  def age_=(newValue: Int) {
    if (newValue > privateAge) privateAge = newValue
  }
}

object Person extends App {
  val person = new Person("Qingwei Song")
  person.age = 2
  person.age = -1
  println(person.age)
}

/*
λ jjavap Person.class                                         
Compiled from "Person.scala"                                  
public class com.ericsson.scala.chapter5.Person {             
  public java.lang.String firstName();                        
  public java.lang.String secondName();                       
  public com.ericsson.scala.chapter5.Person(java.lang.String);
}                                                             
*/