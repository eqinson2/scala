package com.ericsson.scala

object yieldDemo extends App {
  // 以序列形式返回一行乘法表 
  def makeRowSeq(row: Int) =
    for (col <- 1 to 10) yield {
      val prod = (row * col).toString
      val padding = " " * (6 - prod.length)
      padding + prod
    }

  // 以字串形式返回一行乘法表 
  def makeRow(row: Int) = makeRowSeq(row).mkString // 以字串形式返回乘法表，每行记录占一行字串 

  def multiTable() = {
    val tableSeq =
      for (row <- 1 to 100)
        yield makeRow(row)
    tableSeq.mkString("\n")
  }

  println(multiTable)
}
  
