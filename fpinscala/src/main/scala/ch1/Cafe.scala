package ch1

case class CreditCard(name: String) {
}

case class Coffee() {
  val price: Double = 1.0
  override def toString = "Coffee with price=" + price
}

case class Charge(cc: CreditCard, amount: Double) {
  def combine(other: Charge): Charge =
    if (cc == other.cc)
      Charge(cc, amount + other.amount)
    else throw new Exception("Can not combine charges to different cards")

  override def toString = "Pay with creditCard of " + cc.name + " with price=" + amount
}

object Cafe extends App {
  def buyCoffee(cc: CreditCard): (Coffee, Charge) = {
    val cup = Coffee()
    (cup, Charge(cc, cup.price))
  }

  def buyCoffees(cc: CreditCard, n: Int): (List[Coffee], Charge) = {
    val purchases = List.fill(n)(buyCoffee(cc))
    val (coffees, charges) = purchases.unzip
    (coffees, charges.reduceLeft(_ combine _))
  }

  def coalesce(charges: List[Charge]): List[Charge] =
    charges.groupBy(_.cc).values.map(_.reduceLeft(_ combine _)).toList
    
  val result = Cafe.buyCoffees(CreditCard("eqinson"), 3)
  val result2 = Cafe.buyCoffees(CreditCard("eqinson2"), 3)
  val result3 = Cafe.buyCoffees(CreditCard("eqinson"), 3)
  
  println(result._1, result._2)
  println(Cafe.coalesce(result._2 :: result2._2 :: result3._2 :: Nil))
}