package com.ericsson.cmccmmlbatch.misc

import java.nio.file.{Files, Path, Paths}
import java.util.Date

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.event.LoggingReceive
import com.ericsson.cmccmmlbatch.SubSystem
import com.ericsson.cmccmmlbatch.config.BatchHandlerConfigProvider
import com.ericsson.cmccmmlbatch.exception.{ShutdownFailureException, StartupFailureException}
import org.apache.logging.log4j.LogManager

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

object AgedReportRecycleService extends SubSystem {
  private val logger = LogManager.getLogger(AgedReportRecycleService)

  private var myIsRunning = false

  private var actorSystem: Option[ActorSystem] = None

  private var houseKeeper: Option[ActorRef] = None

  @throws(classOf[StartupFailureException])
  def startup() = {
    synchronized {
      if (myIsRunning) {
        throw new StartupFailureException("AgedReportRecycle is already running")
      }

      actorSystem = Some(ActorSystem("AgedReportRecycleService"))
      houseKeeper = Some(actorSystem.get.actorOf(Props[AgedReportRecycleActor], "AgedReportRecycleActor"))

      myIsRunning = true
      logger.info("AgedReportRecycle startup now...")
    }
  }

  @throws(classOf[ShutdownFailureException])
  def shutdown(theGracefulShutdownMode: Boolean) = {
    synchronized {
      if (!this.isRunning) {
        throw new ShutdownFailureException("AgedReportRecycle is already stopped!")
      }

      actorSystem.get.stop(houseKeeper.get)
      actorSystem.get.shutdown()

      myIsRunning = false
      logger.info("AgedReportRecycle shutdown now...")
    }
  }

  def isRunning = myIsRunning

  def getName = "AgedReportRecycle"
}

class AgedReportRecycleActor extends Actor with ActorLogging with BatchHandlerConfigProvider {

  import java.util.concurrent.TimeUnit._
  import scala.concurrent.ExecutionContext.Implicits.global

  val initialDelay = new FiniteDuration(1, SECONDS)
  val interval = new FiniteDuration(1, DAYS)
  context.system.scheduler.schedule(initialDelay, interval, self, Tick)

  override def receive = LoggingReceive {
    case Tick => processHouseKeeping()
    case msg => log.warning("Received unexpected message {}", msg)

  }

  private def processHouseKeeping() = {
    log.info("start to do AgedReportRecycle.")

    val numDays = BHConfiguration.reportKeepDay.get
    val output = BHConfiguration.outputReportDir.get

    def nameFilter(p: Path) = {
      val f = p.toFile
      f.isFile && (f.getName.endsWith(".ack") || f.getName.endsWith(".err") || f.getName.endsWith(".badsyntax"))
    }

    def timeFilter(p: Path) = (new Date().getTime - p.toFile.lastModified) > numDays * (24 * 60 * 60 * 1000)

    def delete(p: Path) = {
      log.debug("aged file {} deleted", p.toAbsolutePath.toFile.delete)
    }

    import scala.collection.JavaConversions._
    Files.newDirectoryStream(Paths get output).filter(nameFilter).filter(timeFilter).foreach(delete)

    log.info("finish to do AgedReportRecycle.")
  }

  private case object Tick

}