package com.ericsson.cp

object SynchronizedGracefulShutdown extends App {
  import scala.collection._
  import scala.annotation.tailrec

  private val tasks = mutable.Queue[() => Unit]()

  object Worker extends Thread {
    @volatile var terminated = false

    def poll(): Option[() => Unit] = tasks.synchronized {
      while (tasks.isEmpty && !terminated) tasks.wait()
      if (!terminated) Some(tasks.dequeue()) else None
    }

    @tailrec override def run() = poll() match {
      case Some(task) =>
        task(); run();
      case None =>
    }

    def shutdown() = tasks.synchronized {
      terminated = true
      tasks.notify()
    }
  }

  Worker.start()

  def asynchronous(body: => Unit) = tasks.synchronized {
    tasks.enqueue(() => body)
    tasks.notify()
  }

  asynchronous { println("Hello ") }
  asynchronous { println("World!") }

  Thread.sleep(1000)

  Worker.shutdown()
}