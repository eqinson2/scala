/* 
 * Created: 9 dec 2014
 * 
 * Copyright (c) 2014 Ericsson AB, Sweden. 
 * All rights reserved. 
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden. 
 * The program(s) may be used and/or copied with the written permission from Ericsson AB 
 * or in accordance with the terms and conditions stipulated in the agreement/contract 
 * under which the program(s) have been supplied. 
 */
package ch3

/**
 * Companion object to [[com.ericsson.mdv.engine.referencedata.ReferenceData]].
 */
object ReferenceData {
  /**
   * Singleton reference data for administrative use to indicate no execution data.
   */
  final val EmptyReferenceData: ReferenceData = new ReferenceData{
    override def receivedValues(path: String): List[(String, Option[String])] = List.empty
    override def createdValues(path: String): List[(String, Option[String])] = List.empty
  }
}

/**
 * Entity defining the interface to reference data.
 */
trait ReferenceData {

  /**
   * Gets the requested data categorized as received data in this reference data.
   * @param path is the identifier for the data.
   * @return a list of the requested data (path, value). None for values indicate emptiness. Empty list means no values found.
   */
  def receivedValues(path: String): List[(String, Option[String])]

  /**
   * Gets the requested data categorized as created data in this reference data.
   * @param path is the identifier for the data.
   * @return a list of the requested data (path, value). None for values indicate emptiness. Empty list means no values found.
   */
  def createdValues(path: String): List[(String, Option[String])]
}