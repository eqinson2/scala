package com.ericsson.cmccmmlbatch

import com.ericsson.cmccmmlbatch.misc.AgedReportRecycleService
import com.ericsson.cmccmmlbatch.telnetinterface.server.TelnetInterfaceService
import org.apache.logging.log4j.LogManager

object MainApp extends App {
  private val services: List[SubSystem] = TelnetInterfaceService :: AgedReportRecycleService :: Nil

  services.foreach(_.startup)
}