package com.ericsson.scala.ClassExtends

abstract class Person(val name: String, val age: Int, val gender: String) {
  def work: Unit
}

class Employee(name: String, age: Int, gender: String, val company: String) extends Person(name, age, gender) {
  override def work: Unit = { println(s"work in $company with age=$age and gender=$gender") }
}

class Engineer(name: String, age: Int, gender: String, company: String) extends Employee(name, age, gender, company) {
  override def work: Unit = { super.work; println(s"work in $company as engineer") }
}

object Test2 extends App {
  val me = new Engineer("eqinson", 35, "male", "E///")
  me.work
  println(me.name)
}