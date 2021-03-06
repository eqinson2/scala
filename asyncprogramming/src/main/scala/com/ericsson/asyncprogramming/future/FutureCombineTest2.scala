package com.ericsson.asyncprogramming.future

import java.text.SimpleDateFormat
import java.util.Date

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.Failure
import scala.util.Random
import scala.util.Success

object FutureCombineTest2 extends App {
  type CoffeeBeans = String
  type GroundCoffee = String
  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  case class Water(temperature: Int)
  case class GrindingException(msg: String) extends Exception(msg)
  case class FrothingException(msg: String) extends Exception(msg)
  case class WaterBoilingException(msg: String) extends Exception(msg)
  case class BrewingException(msg: String) extends Exception(msg)

  def log(s: String) = {
    val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    val date = new Date();
    println(dateFormat.format(date) + " " + s)
  }

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    log("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans")
      throw GrindingException("are you joking?")
    //log("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    log("heating the water now: " + water.temperature)
    Thread.sleep(2000)
    log("hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    log("milk frothing system engaged!")
    Thread.sleep(2000)
    //log("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    log("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    //log("it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  def prepareCappuccinoSequentially(): Future[Cappuccino] =
    for {
      //groundCoffee,heatedWater,frothedMilk run sequentially
      ground <- grind("arabica beans")
      water <- heatWater(Water(35))
      foam <- frothMilk("milk")
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)

  def prepareCappuccino(): Future[Cappuccino] = {
    //groundCoffee,heatedWater,frothedMilk run in parallel and brew will wait above 3 future to avail
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(40))
    val frothedMilk = frothMilk("milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)
  }

  //prepareCappuccinoSequentially
  prepareCappuccino

  Thread.sleep(10 * 1000)

}