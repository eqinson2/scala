package com.ericsson.scala.Matches

object leafSum extends App {
  def leafSum(nodes: List[Any]): Int = {
    var sum: Int = 0
    for (n <- nodes) {
      n match {
        case x: Int     => sum += x
        case l: List[_] => sum += leafSum(l)
      }
    }
    sum
  }

  val list = List(List(3, 8), 2, List(5))
  println(leafSum(list))

  sealed abstract class BinaryTree
  case class Leaf(value: Int) extends BinaryTree
  case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree

  def leafSum2(node: BinaryTree): Int = {
    var sum: Int = 0
    node match {
      case Leaf(x)    => sum += x
      case Node(x, y) => sum += leafSum2(x) + leafSum2(y)
    }
    sum
  }
}