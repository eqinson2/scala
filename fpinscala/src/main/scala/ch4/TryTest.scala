package ch4

import java.net.URL
import scala.util.Try
import java.io.InputStream
import scala.io.Source
import scala.util.Failure
import scala.util.Success
import java.io.FileNotFoundException
import java.net.MalformedURLException

object TryTest extends App {
  def parseURL(url: String): Try[URL] = Try(new URL(url))

  val url = parseURL(Console.readLine("URL: ")) getOrElse new URL("http://www.baidu.com")
  println(parseURL("http://danielwestheide.com").map(_.getProtocol).getOrElse("unknown protocol"))
  // results in Success("http")
  println(parseURL("garbage").map(_.getProtocol).getOrElse("unknown protocol"))

  def inputStreamForURL(url: String): Try[Try[Try[InputStream]]] = parseURL(url).map { u =>
    Try(u.openConnection()).map(conn => Try(conn.getInputStream))
  }

  def inputStreamForURL2(url: String): Try[InputStream] = parseURL(url).flatMap {
    u => Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
  }

  def parseHttpURL(url: String) = parseURL(url).filter(_.getProtocol == "http")
  parseHttpURL("http://apache.openmirror.de") // results in a Success[URL]
  parseHttpURL("ftp://mirror.netcologne.de/apache.org") // results in a Failure[URL]

  parseHttpURL("http://danielwestheide.com").foreach(println)

  def getURLContent(url: String): Try[Iterator[String]] =
    for {
      url <- parseURL(url)
      connection <- Try(url.openConnection())
      is <- Try(connection.getInputStream)
      source = Source.fromInputStream(is)
    } yield source.getLines()

  getURLContent("http://danielwestheide.com/foobar") match {
    case Success(lines) => lines.foreach(println)
    case Failure(ex)    => println(s"Problem rendering URL content: ${ex.getMessage}")
  }

  val content = getURLContent("garbage") recover {
    case e: FileNotFoundException => Iterator("Requested page does not exist")
    case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
    case _                        => Iterator("An unexpected error has occurred. We are so sorry!")
  }
}