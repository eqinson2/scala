package com.ericsson.scala.Implicit

abstract class TypeToObtain { type t }
object aString extends TypeToObtain { type t = String }
object anInt extends TypeToObtain { type t = Int }
object aDouble extends TypeToObtain { type t = Double }

class Obtain() {
  var useNextArgAs: Any = _
  var name: aString.t = _
  var age: anInt.t = _
  var weight: aDouble.t = _

  def askingFor(ask: String) = {
    print(ask + ": ")
    useNextArgAs match {
      case `aString` => name = readLine
      case `anInt`   => age = readInt
      case `aDouble` => weight = readDouble
      case _         =>
    }
    this
  }

  def and(what: TypeToObtain) = { useNextArgAs = what; this }

  override def toString = "name:" + name + " age:" + age + " weight:" + weight
}

object Obtain {
  def apply(what: TypeToObtain) = new Obtain().and(what)
}

object Problem4 extends App {
  // Does not work with Obtain without parentheses: "Obtain aString".
  val o = Obtain(aString) askingFor "Your name" and anInt askingFor "Your age" and aDouble askingFor "Your weight"
  println(o)
}