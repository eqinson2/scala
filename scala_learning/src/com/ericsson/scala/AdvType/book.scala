package com.ericsson.scala.AdvType

object Title
object Author
object Price

class Document {
  private var title: String = _
  private var author: String = _
  var useNextArgAs: Any = null
  def set(obj: Title.type): this.type = { useNextArgAs = obj; this }
  def set(obj: Author.type): this.type = { useNextArgAs = obj; this }
  def to(args: String): this.type = {
    useNextArgAs match {
      case Title  => title = args
      case Author => author = args
      case _      =>
    }
    this
    /*if (useNextArgAs == Title) title = args;
    else if (useNextArgAs == Author) author = args
    this*/
  }

  override def toString = "Title:" + title + " | Author:" + author
}

class Book extends Document {
  private var price: Double = _
  def With(obj: Price.type): this.type = { useNextArgAs = obj; this }
  def as(args: Double): this.type = {
    if (useNextArgAs == Price) price = args
    this
  }
  override def toString = super.toString() + "| price:" + price
}

object Book

object TEST extends App {
  val book = new Book
  book set Title to "Scala for the Impatient" set Author to "Cay Horstman" With Price as 1.30D
  println(book)
}