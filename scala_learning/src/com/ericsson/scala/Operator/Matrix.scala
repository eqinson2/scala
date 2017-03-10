package com.ericsson.scala.Operator

class Matrix(val row: Int, val colomn: Int) {
  val matrix: Array[Array[Int]] = new Array[Array[Int]](row)
  for (i <- 0 until matrix.length)
    matrix(i) = new Array[Int](colomn)

  def apply(m: Int, n: Int) = matrix(m)(n)
  def update(m: Int, n: Int, v: Int) = { matrix(m)(n) = v }

  def +(another: Matrix) = {
    if (row != another.row || colomn != another.colomn) this
    else {
      val newMatrix = new Matrix(row, colomn)
      for (i <- 0 until row; j <- 0 until colomn)
        newMatrix(i, j) = matrix(i)(j) + another(i, j)
      newMatrix
    }
  }

  def +(adder: Int) = {
    val newMatrix = new Matrix(row, colomn)
    for (i <- 0 until row; j <- 0 until colomn)
      newMatrix(i, j) = matrix(i)(j) + adder
    newMatrix
  }

  def *(multiplier: Int) = {
    val newMatrix = new Matrix(row, colomn)
    for (i <- 0 until row; j <- 0 until colomn)
      newMatrix(i, j) = matrix(i)(j) * multiplier
    newMatrix
  }

  def printMatrix = {
    for (i <- 0 until row) {
      for (j <- 0 until colomn)
        print(matrix(i)(j) + "  ")
      println
    }
  }
}

object Matrix extends App {
  val m = new Matrix(3, 10)
  m.printMatrix
  println
  (m * 2).printMatrix
  println
  (m + 3).printMatrix
  println
  (m + m + 4).printMatrix

  println((m + m + 4)(1, 2))
  m(1, 2) = 3
  m.printMatrix
}