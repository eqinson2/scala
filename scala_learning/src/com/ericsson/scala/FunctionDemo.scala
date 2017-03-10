package com.ericsson.scala

object FunctionDemo extends App {
  var x = 0
  var y = 1

  val a = {
    x + y;
    x - y
  }
  //a is int
  val b = {
    x + y;
    x = x + y
  } //b is Unit

  println(a)
  println(b)

  def func(param: Int) = if (param > 0) 1 else if (param < 0) -1 else 0

  println(func(3))

  val d = {}

  var X: Unit = null
  var Y = 4
  X = Y = 1

  println((1 to 10).reverse.mkString("\n"))

  def countdown(n: Int) = (1 to n).reverse.foreach(println(_))

  countdown(100)

  def product(s: String) = (1 /: s.toList) ((a, b) => a * b)

  println(product("Hello"))

  def products(s: Char*): Int = if (s.isEmpty) 1 else s.head * products(s.tail: _*)

  println(products("Hello".toList: _*))

  def fun(x: Int, n: Int): Int = {
    if (n == 0) 1
    else if (n > 0 && n % 2 == 0) {
      val y = fun(x, n / 2);
      y * y
    }
    else if (n > 0 && n % 2 == 1) {
      x * fun(x, n - 1)
    }
    else {
      1 / fun(x, -n)
    }
  }

  println(fun(3, 100))
}