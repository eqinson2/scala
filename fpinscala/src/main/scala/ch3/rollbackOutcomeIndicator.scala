/* 
 * Created: 09 jun 2016
 * 
 * Copyright (c) 2016 Ericsson AB, Sweden. 
 * All rights reserved. 
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden. 
 * The program(s) may be used and/or copied with the written permission from Ericsson AB 
 * or in accordance with the terms and conditions stipulated in the agreement/contract 
 * under which the program(s) have been supplied. 
 */
package ch3

/**
  * A sealed abstract class that defines all outcomes handled at rollback.
  */
sealed abstract class RollbackOutcomeIndicator

/** Indicator for the case where rollback is defined and executed. */
case object RollbackExecuted extends RollbackOutcomeIndicator

/** Indicator for the case where rollback is defined but not executed typically when earlier tasks rollback fails. */
case object RollbackNotExecuted extends RollbackOutcomeIndicator

/** Indicator for the case where no rollback is defined. */
case object NoRollbackDefined extends RollbackOutcomeIndicator

/** Indicator for the case where the precondition is not fulfilled. */
case object PreconditionNotFulfilled extends RollbackOutcomeIndicator