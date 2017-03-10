package com.ericsson.cmccmmlbatch.batchgenerator.executor

import java.util.concurrent.CountDownLatch

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, actorRef2Scala}
import akka.event.LoggingReceive
import com.ericsson.cmccmmlbatch.SubSystem
import com.ericsson.cmccmmlbatch.batchgenerator.parser.BatchJobParser
import com.ericsson.cmccmmlbatch.batchgenerator.render.BatchFileRender
import com.ericsson.cmccmmlbatch.config.BatchFileRenderConfigProvider
import org.apache.logging.log4j.LogManager

object RenderExecutor

class RenderExecutor(val parser: BatchJobParser, val numberOfSubscribers: Int) extends SubSystem with BatchFileRenderConfigProvider {
  private val logger = LogManager.getLogger(RenderExecutor)
  private[this] val countDown = new CountDownLatch(1)
  @volatile var running = false
  private[this] var joinerActor: ActorRef = null
  private[this] var system: ActorSystem = null
  private[this] var start: Long = _

  override final def isRunning = running

  override final def getName = "RenderExecutor"

  override def startup() = {
    start = System.currentTimeMillis

    val nPieces = math.ceil(numberOfSubscribers.toFloat / RenderConfiguration.maxCommandPerOutputBatchFile.get).toInt
    val pieceSize = RenderConfiguration.maxCommandPerOutputBatchFile.get
    logger.info("job is divided into {} pieces, {} pieceSize.", nPieces, pieceSize)

    system = ActorSystem("RenderExecutor")
    joinerActor = system.actorOf(Props(new JoinerActor(parser, nPieces, pieceSize, countDown)), "JoinerActor")
    joinerActor ! signal.start
    running = true
  }

  override def shutdown(theGracefulShutdownMode: Boolean): Unit = {
    if (!running) return
    else if (theGracefulShutdownMode) waitForComplete()
    else system.stop(joinerActor)
    system.shutdown()
  }

  def waitForComplete() = {
    try {
      countDown.await()
      logger.info("RenderExecutor completed after {} ms", System.currentTimeMillis - start)
    } catch {
      case e: InterruptedException =>
    }
    running = false
  }
}

package object signal {

  case class finished(number: Int)

  case class rendering(subs: java.util.List[String])

  case object start

}

class JoinerActor(val parser: BatchJobParser, val nPieces: Int, val pieceSize: Int, val countDown: CountDownLatch) extends Actor with ActorLogging {
  lazy val system = ActorSystem("JoinerActorSystem")
  var piecesReceived = 0

  def receive: Receive = LoggingReceive {
    case signal.start =>
      log.info("JoinerActor receive start signal.")
      val buffer = new java.util.ArrayList[String]

      var end = false
      while (!end) {
        val sub = parser.getNextSubscriber
        if (sub != null) buffer.add(sub)
        else end = true
      }

      for (id <- 0 until nPieces) {
        log.info("subscriber start rendering, jobid: {}", id)
        val piece = buffer.subList(pieceSize * id, math.min(pieceSize * (id + 1), buffer.size))
        val render = system.actorOf(Props(new RenderActor(id)), "render_" + id)
        render ! signal.rendering(piece)
      }


    case signal.finished(number) =>
      log.info("JoinerActor is notified {} subscriber has finished rendering", number)
      piecesReceived += 1
      if (piecesReceived == nPieces)
        countDown.countDown()

  }
}

class RenderActor(val id: Int) extends Actor with ActorLogging {
  def receive: Receive = LoggingReceive {
    case signal.rendering(subs) =>
      log.info("RenderActor_{} receive rendering signal.", id)
      val bfRender = new BatchFileRender(id)
      log.info("rendering size:{}", subs.size)
      bfRender.render(subs)
      sender ! signal.finished(subs.size)
      context.stop(self)
  }
}