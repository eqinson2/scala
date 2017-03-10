package com.ericsson.scala.Matches

object eval extends App {
  sealed abstract class BinaryTree
  case class Leaf(value: Int) extends BinaryTree
  case class Node(ch: Char, tr: BinaryTree*) extends BinaryTree

  def eval(node: BinaryTree): Int = {
    node match {
      case Leaf(x)                => x
      case Node('*', child @ _*)  => (1 /: child)(_ * eval(_))
      case Node('/', left, right) => eval(left) / eval(right)
      case Node('+', child @ _*)  => child.map { eval }.sum
      case Node('-', left, right) => eval(left) - eval(right)
      case Node('-', child)       => -eval(child)
    }
  }
  val r = Node('+', Node('*', Leaf(3), Leaf(8), Leaf(3), Leaf(8)), Leaf(2), Node('-', Leaf(5), Leaf(5)), Node('-', Leaf(5)))
  println(eval(r))
}