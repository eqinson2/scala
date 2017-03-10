package com.ericsson.scala

abstract class Check {
  def check(): String
}

class RealCheck(val name: String) extends Check {
  def check(): String = name + " Checked Application Details..."
}

trait CreditCheck extends Check {
  val name: String
  abstract override def check(): String = name + " Checked credit..." + super.check()
}

trait EmploymentCheck extends Check {
  val name: String
  abstract override def check(): String = name + " Checked Employment..." + super.check()
}

trait CriminalRecordCheck extends Check {
  val name: String
  abstract override def check(): String = name + " Checked Criminal..." + super.check()
}

object Decorator extends App {
  val apartmentApplication = new RealCheck("eqinson1") with CreditCheck with CriminalRecordCheck
  println(apartmentApplication check)

  val employmentApplication = new RealCheck("eqinson2") with CriminalRecordCheck with EmploymentCheck
  println(employmentApplication check)
}