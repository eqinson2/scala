package com.ericsson.scala.AnnotationDemo

import scala.annotation.varargs
import scala.annotation.tailrec

class Sum {
  @varargs def sum(n: Int*) = {
    n.sum
  }

  //@tailrec
  private def sum2(xs: Seq[Int]): BigInt = {
    if (xs.isEmpty) 0 else xs.head + sum2(xs.tail)
  }

  @tailrec
  private def sum3(xs: Seq[Int], partial: BigInt): BigInt = {
    if (xs.isEmpty) partial else sum3(xs.tail, xs.head + partial)
  }

} 