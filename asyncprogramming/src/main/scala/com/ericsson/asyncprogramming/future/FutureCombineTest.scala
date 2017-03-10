package com.ericsson.asyncprogramming.future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Await
import java.text.DateFormat
import java.util.Date
import java.text.SimpleDateFormat

object FutureCombineTest extends App {
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

  //  grind("baked beans").onComplete {
  //    case Success(ground) => log(s"got my $ground")
  //    case Failure(ex)     => log("This grinder needs a replacement, seriously!")
  //  }

  def temperatureOkay(water: Water): Future[Boolean] = Future {
    log("testing if temperatureOkay")
    (80 to 85) contains (water.temperature)
  }

  log("creating nestedFuture")
  val nestedFuture: Future[Future[Boolean]] = heatWater(Water(20)) map {
    water => temperatureOkay(water)
  }

  //once heatWater finished, temperatureOkay will be invoked as callback
  log("creating flatFuture")
  val flatFuture: Future[Boolean] = heatWater(Water(25)) flatMap {
    water => temperatureOkay(water)
  }

  flatFuture.onComplete {
    case Success(result) => log("***************" + result + "***************")
    case Failure(ex)     => log("XXXXXXXXXXXXXXXX" + ex + "XXXXXXXXXXXXXX")
  }

  val acceptable: Future[Boolean] = for {
    heatedWater <- heatWater(Water(30))
    okay <- temperatureOkay(heatedWater)
  } yield okay //等效于flatMap

  log("Await.result:" + Await.result(nestedFuture.flatMap(identity), 20.seconds))

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

  prepareCappuccinoSequentially
  prepareCappuccino

  Thread.sleep(10 * 1000)
}