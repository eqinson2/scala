package org.nicholasren.scala.stock

object ConsoleInput {
  def main(args: Array[String]) {
    print("Please enter a ticker synbol : ")

    val symbol = Console.readLine

    println("OK, got it, you own " + symbol)
  }
}