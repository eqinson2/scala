/* 
 * Created: 10 dec 2014
 * 
 * Copyright (c) 2014 Ericsson AB, Sweden. 
 * All rights reserved. 
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden. 
 * The program(s) may be used and/or copied with the written permission from Ericsson AB 
 * or in accordance with the terms and conditions stipulated in the agreement/contract 
 * under which the program(s) have been supplied. 
 */
package ch3

import scala.collection.immutable.ListMap
import scala.concurrent.Future
import scala.util.Try

package object engine {
  /**
   *  This name is used as the task name for referencing the request data.
   */
  val RequestName: String = "mdvnbrequest"

  /**
   * This is the container type for task name to future execution response mapping.
   */
  type ResultMap = Map[String, Future[TaskReferenceData]]

  /**
   * Companion for the [[com.ericsson.mdv.engine.ResultMap]] type.
   */
  object ResultMap {
    /**
     * Factory method.
     * @param elems is a repeatable parameter that maps name to future task reference data.
     * @return the created result map.
     */
    def apply(elems: (String, Future[TaskReferenceData])*): Map[String, Future[TaskReferenceData]] = {
      Map[String, Future[TaskReferenceData]](elems: _*)
    }
  }

  /**
   * This is the container type for ordered task name to execution failure/rollback execution outcome mapping.
   */
  type FailureResultMap = ListMap[String, List[Try[RollbackOutcomeIndicator]]]

  /**
   * Companion for the [[com.ericsson.mdv.engine.FailureResultMap]] type.
   */
  object FailureResultMap {
    /** The empty map of this type. */
    val empty = FailureResultMap()

    /**
     * Factory method.
     * @param elems is a repeatable parameter that maps task name to a list of rollback outcome.
     * @return the created failure result map.
     */
    def apply(elems: (String, List[Try[RollbackOutcomeIndicator]])*): ListMap[String, List[Try[RollbackOutcomeIndicator]]] = {
      ListMap[String, List[Try[RollbackOutcomeIndicator]]](elems: _*)
    }
  }
}