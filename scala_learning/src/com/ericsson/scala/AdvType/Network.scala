package com.ericsson.scala.AdvType

import scala.collection.mutable.ArrayBuffer

class Network {
  class Member(val name: String) {
    val contacts = new ArrayBuffer[Member]

    override def equals(other: Any): Boolean = {
      type NetworkMemberType = n.Member forSome { val n: Network }
      //      type NetworkMemberType = Member
      //      type NetworkMemberType = Network#Member
      other match {
        case t: NetworkMemberType if (other.asInstanceOf[NetworkMemberType].name == name) => true
        case _ => false
      }
    }
  }

  class NetworkMember1(name: String) extends Member(name) {
  }

  def process1[M <: n.Member forSome { val n: Network }](m1: M, m2: M) = (m1, m2)

  type NetworkMember = n.Member forSome { val n: Network }
  def process2(m1: NetworkMember, m2: NetworkMember) = (m1, m2)

  private val members = new ArrayBuffer[Member]

  def join(name: String) = {
    val m = new Member(name)
    members += m
    m
  }
}

object NetworkTest extends App {
  val chatter = new Network
  val myFace = new Network
  val fred = chatter.join("Fred")
  val barney = myFace.join("Barney")
  val fred2 = myFace.join("Fred")
  val fred3 = chatter.join("Fred")
  
  
  //fred.contacts += barney
  println(fred == barney)
  println(fred == fred2)
  println(fred == fred3)

  chatter.process1(fred, fred3)
  //chatter.process1(fred2, fred3)
  chatter.process2(fred2, fred3)
}