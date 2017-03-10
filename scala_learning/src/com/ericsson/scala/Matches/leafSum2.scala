package com.ericsson.scala.Matches

object leafSum2 extends App {
  sealed abstract class BinaryTree
  case class Leaf(value: Int) extends BinaryTree
  case class Node(chile: BinaryTree*) extends BinaryTree

  def leafSum(node: BinaryTree): Int = {
    var sum: Int = 0
    node match {
      case Leaf(x)          => sum += x
      case Node(child @ _*) => sum += (0 /: child)(_ + leafSum(_))
    }
    sum
  }

  val list = Node(Node(Node(Leaf(3), Leaf(8)), Node(Leaf(3), Leaf(8)), Leaf(2)), Leaf(5), Node(Leaf(3), Leaf(8)))
  println(leafSum(list))
}