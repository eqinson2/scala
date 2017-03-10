package com.ericsson.scala

import scala.actors.Actor._

object ActorDemo {
  def main(args: Array[String]) =
    {
      val myActor =
        actor {
          receive {
            case msg => println(msg)
          }
        }

      myActor ! "Do ya feel lucky, punk?"
    }
}