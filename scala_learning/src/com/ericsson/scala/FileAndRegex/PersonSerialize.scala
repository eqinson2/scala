package com.ericsson.scala.FileAndRegex

import scala.collection.mutable.ArrayBuffer

@SerialVersionUID(42L) class Person(val name: String) extends java.io.Serializable {
  val friends: ArrayBuffer[Person] = new ArrayBuffer[Person]

  def mkFriendWith(other: Person) = friends.+=(other)

  def printAllFriends() = friends.map { s => println(s.name) }
}

object Person extends App {
  val fred = new Person("fred")
  val tim = new Person("tim")
  val jack = new Person("jack")
  val me = new Person("eqinson")

  fred mkFriendWith tim
  fred mkFriendWith jack
  fred mkFriendWith me

  import java.io._

  val out = new ObjectOutputStream(new FileOutputStream("person.obj"))
  out.writeObject(fred)
  out.close

  val in = new ObjectInputStream(new FileInputStream("person.obj"))
  val savedFred = in.readObject().asInstanceOf[Person]

  savedFred.printAllFriends
}