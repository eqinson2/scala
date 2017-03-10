package com.ericsson.scala.Class

class Car(private val manufacture: String, private val carType: String) {
  private var year: Int = -1
  private var brand: String = ""

  def this(manufacture: String, carType: String, year: Int, brand: String) {
    this(manufacture, carType)
    this.year = year
    this.brand = brand
  }

  def this(manufacture: String, carType: String, year: Int) {
    this(manufacture, carType, year, "")
  }

  def this(manufacture: String, carType: String, brand: String) {
    this(manufacture, carType, -1, brand)
  }

}