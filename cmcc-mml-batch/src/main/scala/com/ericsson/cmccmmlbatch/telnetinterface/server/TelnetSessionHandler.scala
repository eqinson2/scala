package com.ericsson.cmccmmlbatch.telnetinterface.server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated, actorRef2Scala}
import akka.event.LoggingReceive
import akka.io.Tcp
import akka.util.ByteString
import com.ericsson.cmccmmlbatch.config.TelnetServerConfig
import com.ericsson.cmccmmlbatch.telnetinterface.mml.MMLCmdHandler

/**
  * Companion object to the session handler class.
  */
object TelnetSessionHandler {
  def props(socket: InetSocketAddress, connection: ActorRef): Props = Props(new TelnetSessionHandler(socket, connection))
}

/**
  * Session handler.
  *
  * @constructor creates a new instance of the SessionHandler.
  * @param socket     is the socket to use.
  * @param connection is the client.
  */
class TelnetSessionHandler(socket: InetSocketAddress, connection: ActorRef) extends Actor with ActorLogging {
  // Sign death pact: this actor terminates when connection to the client breaks (without need of a `Tcp.ConnectionClosed`)
  context.watch(connection)
  // Print the welcome message
  connection ! Tcp.Write(ByteString(TelnetServerConfig.firstMessage))

  val handler = new MMLCmdHandler

  def receive: Receive = LoggingReceive {

    case Tcp.Received(data) =>
      val msg = data.utf8String.trim
      log.info("Received command: {}", msg)
      processMessage(msg)

    case _: Tcp.ConnectionClosed =>
      log.info("Stopping, because connection for remote address {} closed", socket)
      context.stop(self)

    case Terminated(_) =>
      log.info("Stopping, because connection for remote address {} died", socket)
      context.stop(self)

    // Catch of unexpected messages 
    case msg =>
      log.info("Received unexpected message {}", msg)
  }

  /**
    * Process a received application message.
    *
    * @param msg is the message to process.
    */
  private def processMessage(msg: String) {
    val LS = TelnetServerConfig.NewLineWithPrompt
    msg match {
      case "" => connection ! Tcp.Write(ByteString(LS))
      case "quit" | "exit" => handleQuit()
      case m =>
        connection ! Tcp.Write(ByteString(handler.handleInput(m) + LS))
        if (msg == "LOGOUT;") handleQuit()
    }
  }

  /** Handle the quit and exit commands. */
  private def handleQuit() {
    connection ! Tcp.Write(ByteString(s"${TelnetServerConfig.LS}"))
    context.stop(self)
  }
}
