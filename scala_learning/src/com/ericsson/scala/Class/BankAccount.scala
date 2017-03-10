package com.ericsson.scala.Class

class BankAccount {
  var balance: Int = 0

  def deposit(amount: Int) = if (amount < balance) balance += amount

  def withDraw(amount: Int) = balance += amount
}