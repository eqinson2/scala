package ch4

import scala.concurrent.future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random
import scala.util.Failure
import scala.util.Success
import scala.concurrent.Await

object FutureTest extends App {
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

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("start grinding...")
    Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans")
      throw GrindingException("are you joking?")
    //println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("heating the water now")
    Thread.sleep(2000)
    println("hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("milk frothing system engaged!")
    Thread.sleep(2000)
    //println("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future {
    println("happy brewing :)")
    Thread.sleep(Random.nextInt(2000))
    //println("it's brewed!")
    "espresso"
  }

  //  grind("baked beans").onComplete {
  //    case Success(ground) => println(s"got my $ground")
  //    case Failure(ex)     => println("This grinder needs a replacement, seriously!")
  //  }

  def temperatureOkay(water: Water): Future[Boolean] = Future {
    println("testing if temperatureOkay")
    (80 to 85) contains (water.temperature)
  }

  println("creating nestedFuture")
  val nestedFuture: Future[Future[Boolean]] = heatWater(Water(25)) map {
    water => temperatureOkay(water)
  }

  println("creating flatFuture")
  val flatFuture: Future[Boolean] = heatWater(Water(25)) flatMap {
    water => temperatureOkay(water)
  }

  flatFuture.onComplete {
    case Success(result) => println("******************************")
    case Failure(ex)     => println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
  }

  val acceptable: Future[Boolean] = for {
    heatedWater <- heatWater(Water(25))
    okay <- temperatureOkay(heatedWater)
  } yield okay//等效于flatMap

  println("Await.result:" + Await.result(nestedFuture.flatMap(identity), 20.seconds))

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  def prepareCappuccinoSequentially(): Future[Cappuccino] =
    for {
      ground <- grind("arabica beans")
      water <- heatWater(Water(25))
      foam <- frothMilk("milk")
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)

  println

  def prepareCappuccino(): Future[Cappuccino] = {
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew(ground, water)
    } yield combine(espresso, foam)
  }

  println
  prepareCappuccinoSequentially
  prepareCappuccino

  Thread.sleep(10 * 1000)
}