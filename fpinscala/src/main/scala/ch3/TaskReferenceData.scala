/* 
 * Created: 7 jan 2016
 * 
 * Copyright (c) 2016 Ericsson AB, Sweden. 
 * All rights reserved. 
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden. 
 * The program(s) may be used and/or copied with the written permission from Ericsson AB 
 * or in accordance with the terms and conditions stipulated in the agreement/contract 
 * under which the program(s) have been supplied. 
 */
package ch3

import scala.util.Try

/**
 * A class for keeping multiple task result data.
 * @constructor Creates a new instance.
 * @param instances is a list of Try-wrapped reference data that signals the outcome of an individual instance execution.
 * @param seedValues is optional list of seed values that identifies the seed. The order of seed values should matter 
 * when matching seeds. If set to None no seed matching will take place.
 */
case class TaskReferenceData(instances: List[Try[ReferenceData]], seedValues: Option[List[(String, String)]] = None) {
  
  /**
   * Status of task result, true if all instances executed successfully, false if not.
   */
  lazy val isSuccess = !instances.exists(_.isFailure)
}