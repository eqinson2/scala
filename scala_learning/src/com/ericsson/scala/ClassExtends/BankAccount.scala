package com.ericsson.scala.ClassExtends

class BankAccount(initialBalance: Double) {
  private var balance = initialBalance
  def deposit(amount: Double) = { balance += amount; balance }
  def withDraw(amount: Double) = { balance -= amount; balance }
}

class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance: Double) {
  override def deposit(amount: Double) = { super.deposit(amount - 1) }
  override def withDraw(amount: Double) = { super.withDraw(amount + 1) }
}

class savingsAccount(initialBalance: Double) extends BankAccount(initialBalance: Double) {
  private var count: Int = _
  override def deposit(amount: Double) = {
    if (!earlyMonthlyInterest)
      super.deposit(amount - amount * 0.01)
    else
      super.deposit(amount)
  }
  override def withDraw(amount: Double) = {
    if (!earlyMonthlyInterest)
      super.deposit(amount + amount * 0.01)
    else
      super.deposit(amount)
  }

  private def earlyMonthlyInterest = {
    if (count < 3) { count += 1; true }
    else { count = 0; false }
  }
}