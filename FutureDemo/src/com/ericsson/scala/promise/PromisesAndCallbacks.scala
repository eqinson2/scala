package com.ericsson.scala.promise

object PromisesAndCallbacks extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import org.apache.commons.io.monitor._
  import java.io.File

  def fileCreated(directory: String): Future[String] = {
    val p = Promise[String]

    val fileMonitor = new FileAlterationMonitor(1000)
    val observer = new FileAlterationObserver(directory)
    val listener = new FileAlterationListenerAdaptor {
      override def onFileCreate(file: File) {
        try p.trySuccess(file.getName)
        finally fileMonitor.stop()
      }
    }
    observer.addListener(listener)
    fileMonitor.addObserver(observer)
    fileMonitor.start()

    p.future
  }

  fileCreated(".") foreach {
    case filename => println(s"Detected new file '$filename'")
  }

}