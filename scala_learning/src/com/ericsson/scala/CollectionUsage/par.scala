package com.ericsson.scala.CollectionUsage

import scala.collection.immutable.HashMap

object par extends App {
  for (i <- (0 until 100).par) print(i + " ")
  println
  val array = {
    for (i <- (0 until 100).par) yield i + " "
  }
  println(array.mkString(" "))

  val sum = (0 to 100).par.sum
  println(sum)

  val str = "Mississippi Mississippi"

  val pf: PartialFunction[(HashMap[Char, Int], (Char, Int)), HashMap[Char, Int]] = {
    case (map, (k, v)) => map + (k -> (v + map.getOrElse(k, 0)))
  }

  val newStr = str.par.aggregate(HashMap[Char, Int]())((frequency, c) => frequency + (c -> (frequency.getOrElse(c, 0) + 1)),
    (map1: HashMap[Char, Int], map2: HashMap[Char, Int]) => (map1 /: map2) (pf(_, _)))
  println(newStr)

  val newStr2 = str.par.aggregate(HashMap[Char, Int]())((frequency, c) => frequency + (c -> (frequency.getOrElse(c, 0) + 1)),
    (map1: HashMap[Char, Int], map2: HashMap[Char, Int]) =>
      (HashMap[Char, Int]() /: (map1.keySet ++ map2.keySet)) {
        (map, key) => map + (key -> (map1.getOrElse(key, 0) + map2.getOrElse(key, 0)))
      })
  println(newStr2)
}