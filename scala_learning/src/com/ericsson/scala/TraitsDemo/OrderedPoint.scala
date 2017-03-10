package com.ericsson.scala.TraitsDemo

import java.io.IOException

class OrderedPoint(val x: Int, val y: Int) extends scala.math.Ordered[OrderedPoint] {
  override def compare(that: OrderedPoint): Int = {
    if (x == that.x && y == that.y) 0
    else if (x < that.x || x == that.x && y < that.y) -1
    else 1
  }
}

trait Logged {
  def log(msg: String) = {
    println(msg)
  }
}

trait LoggedException extends Exception with Logged {
  abstract override def log(msg: String) = {
    super.log(getMessage)
  }
}

class UnhappyException extends LoggedException {
  override def getMessage = "eqinson"
}

class UnhappyException2 extends IOException with LoggedException {
  override def getMessage = "eqinson2"
}

/*BitSet<=SortedSet                <=Set<=Collection<=Iterable
	      <=BitSetLike<=SortedSetLike<=Sorted
	                                 <=SetLike<=IterableLike
	                                          <=GenSetLike
																						<=Subtractable
																						<=Parallelizable
*/