package com.ericsson.scala.Operator

object Name {
  def unapply(input: String) = {
    val pos = input.indexOf(" ")
    if (pos == -1) None
    else Some(input.substring(0, pos), input.substring(pos + 1))
  }
}

object Test extends App {
  val author = "Dan Simmons"
  author match {
    case Name(firstName, lastName) => println(s"""Hello, Mr $lastName""")
    case _                         =>
  }
}