package com.ericsson.cmccmmlbatch.telnetinterface.server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, actorRef2Scala}
import akka.event.LoggingReceive
import akka.io.{IO, Tcp}
import com.ericsson.cmccmmlbatch.config.TelnetServerConfigProvider

object TelnetServer {
  def ActorName = "TelnetServer"
}

class TelnetServer extends Actor with ActorLogging with TelnetServerConfigProvider {

  import context.system

  private[this] val telnetHost = TelnetConfiguration.telnetHost
  private[this] val telnetPort = TelnetConfiguration.telnetPort

  startListening()

  /** The reception method of this actor. */
  def receive = LoggingReceive {
    // Handle new connections
    case Tcp.Connected(remote, _) =>
      log.info("New incoming client connection on server: {}", remote)
      // Create a new actor for this session
      sender ! Tcp.Register(context.actorOf(TelnetSessionHandler.props(remote, sender)))

    // The binding failed
    case Tcp.CommandFailed(_: Tcp.Bind) =>
      log.info("Telnet Server failed to bind to host {} and port {}", telnetHost.get, telnetPort.get)

    // The connection succeeded
    case Tcp.Bound(local) =>
      log.info("Telnet Server listening on host {} and port {}", local.getHostName, local.getPort)

    // Catch of unexpected messages 
    case msg => log.debug("Received unexpected message {}", msg)
  }

  private def startListening() = IO(Tcp) ! Tcp.Bind(self, new InetSocketAddress(telnetHost.get, telnetPort.get))
}

