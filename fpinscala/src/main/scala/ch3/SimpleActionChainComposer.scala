package ch3

import scala.collection.immutable.ListMap

case class Data(data: String)
case class RollbackData(data: String)
case class TaskContext(name: String)

/**
 * 柯里化函数
 * 有时会有这样的需求：允许别人一会在你的函数上应用一些参数，然后又应用另外的一些参数。
 *
 * 例如一个乘法函数，在一个场景需要选择乘数，而另一个场景需要选择被乘数。
 *
 * scala> def multiply(m: Int)(n: Int): Int = m * n
 * multiply: (m: Int)(n: Int)Int
 * 你可以直接传入两个参数。
 *
 * scala> multiply(2)(3)
 * res0: Int = 6
 * 你可以填上第一个参数并且部分应用第二个参数。
 *
 * scala> val timesTwo = multiply(2) _
 * timesTwo: (Int) => Int = <function1>
 *
 * scala> timesTwo(3)
 * res1: Int = 6
 * 你可以对任何多参数函数执行柯里化。例如之前的adder函数
 *
 * scala> (adder _).curried
 * res1: (Int) => (Int) => Int = <function1>
 *
 * override def foldLeft[B](z: B)(f: (B, A) => B): B = {
 * var acc = z
 * var these = this
 * while (!these.isEmpty) {
 * acc = f(acc, these.head)
 * these = these.tail
 * }
 * acc
 * }
 *
 */
object SimpleActionChainComposer extends App {
  type ResultMap = ListMap[String, Data]
  type FailureResultMap = ListMap[String, RollbackData]
  val empty = new FailureResultMap

  def compose(taskCtxs: TaskContext*): ResultMap => (ResultMap, () => FailureResultMap) = {
    taskCtxs.foldRight[ResultMap => (ResultMap, () => FailureResultMap)](endAction _)(action(_)(_) _)
  }

  def endAction(opResult: ResultMap): (ResultMap, () => FailureResultMap) = {
    println("End action - execute")
    (opResult, () => empty)
  }

  def action(thisTask: TaskContext)(nextAction: (ResultMap) => (ResultMap, () => FailureResultMap))(opResults: ResultMap): (ResultMap, () => FailureResultMap) = {
    println(s"Task: ${thisTask.name} - executing action")
    nextAction(opResults + (thisTask.name -> Data("eqinson")))
    lazy val undo = new FailureResultMap
    (opResults, () => undo)
  }

  val execTasks = List(TaskContext("1"), TaskContext("2"), TaskContext("3"))
  val actionChain = compose(execTasks: _*)
  println("action chain composed.")
  val (result, _) = actionChain(new ResultMap + ("action" -> Data("initial Data")))
  print(result)
}