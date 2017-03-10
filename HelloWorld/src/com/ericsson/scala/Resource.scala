package com.ericsson.scala

class Resource private () {
  println("Starting transaction...")

  private def cleanup() { println("Ending transaction...") }

  def op1 = println("Operation 1")
  def op2 = println("Operation 2")
  def op3 = println("Operation 3")
}

object Resource {
  def use(codeBlock: Resource => Unit) {
    val resource = new Resource
    try {
      codeBlock(resource)
    } finally {
      resource.cleanup()
    }
  }

  def main(args: Array[String]): Unit = {
    Resource.use { resource => resource.op1; resource.op2; resource.op3 }
  }
}