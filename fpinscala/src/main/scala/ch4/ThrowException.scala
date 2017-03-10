package ch4

object ThrowException extends App {
  def failingFn(i: Int) = {
    val y: Int = throw new Exception("fail")
    try {
      val x = 42 + 5
      print(x + y)
    } catch { case e: Exception => 43 }
  }

  def failingF2n(i: Int) = {
    try {
      val x = 42 + ((throw new Exception("fail")): Int)
      print(x)
    } catch { case e: Exception => 43 }
  }
}