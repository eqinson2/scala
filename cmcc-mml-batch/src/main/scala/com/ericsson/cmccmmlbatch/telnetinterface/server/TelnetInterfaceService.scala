package com.ericsson.cmccmmlbatch.telnetinterface.server

import akka.actor.{ActorRef, ActorSystem, Props}
import com.ericsson.cmccmmlbatch.SubSystem
import com.ericsson.cmccmmlbatch.exception.{ShutdownFailureException, StartupFailureException}
import org.apache.logging.log4j.LogManager

object TelnetInterfaceService extends SubSystem {
  private val logger = LogManager.getLogger(TelnetInterfaceService)

  private var myIsRunning = false

  private var actorSystem: Option[ActorSystem] = None

  private var mmlServer: Option[ActorRef] = None

  @throws(classOf[StartupFailureException])
  def startup() = synchronized {
    if (myIsRunning) {
      throw new StartupFailureException("TelnetInterface is already running")
    }

    actorSystem = Some(ActorSystem("TelnetInterface"))
    mmlServer = Some(actorSystem.get.actorOf(Props[TelnetServer], TelnetServer.ActorName))
    myIsRunning = true

    logger.info("TelnetInterface startup now...")
  }

  @throws(classOf[ShutdownFailureException])
  def shutdown(theGracefulShutdownMode: Boolean) = synchronized {
    if (!this.isRunning) {
      throw new ShutdownFailureException("TelnetInterface is already stopped!")
    }
    actorSystem.get.stop(mmlServer.get)
    actorSystem.get.shutdown()
    myIsRunning = false

    logger.info("TelnetInterface shutdown now...")
  }

  def isRunning = myIsRunning

  def getName = "TelnetInterface"
}