package com.ericsson.cp

object SynchronizedPool extends App {
  import scala.collection._

  private val tasks = mutable.Queue[() => Unit]()

  object Worker extends Thread {
    setDaemon(true)
    def poll() = tasks.synchronized {
      while (tasks.isEmpty) tasks.wait()
      tasks.dequeue()
    }
    override def run() = while (true) {
      val task = poll()
      task()
    }
  }

  Worker.start()

  def asynchronous(body: => Unit) = tasks.synchronized {
    tasks.enqueue(() => body)
    tasks.notify()
  }

  asynchronous { println("Hello ") }
  asynchronous { println("World!") }
  
  Worker.join()
}