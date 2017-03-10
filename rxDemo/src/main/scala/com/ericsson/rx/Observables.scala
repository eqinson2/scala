package com.ericsson.rx

object ObservablesItems extends App {
  import rx.lang.scala._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesItems)
  def log(s: String) = logger.info(s)

  val o = Observable.items("Pascal", "Java", "Scala")
  o.subscribe(name => log(s"learned the $name language"))
  o.subscribe(name => log(s"forgot the $name language"))

}

object ObservablesTimer extends App {
  import rx.lang.scala._
  import scala.concurrent.duration._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesTimer)
  def log(s: String) = logger.info(s)

  val o = Observable.timer(1.second)
  o.subscribe(_ => log(s"Timeout!"))
  o.subscribe(_ => log(s"Another timeout!"))

  Thread.sleep(2000)
}

object ObservablesExceptions extends App {
  import rx.lang.scala._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesExceptions)
  def log(s: String) = logger.info(s)

  val o = Observable.items(1, 2) ++ Observable.error(new RuntimeException) ++ Observable.items(3, 4)
  o.subscribe(
    x => log(s"number $x"),
    t => log(s"an error occurred: $t"))
}

object ObservablesLifetime extends App {
  import rx.lang.scala._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesLifetime)
  def log(s: String) = logger.info(s)

  val classics = List("Il buono, il brutto, il cattivo.", "Back to the future", "Die Hard")
  val o = Observable.from(classics)

  o.subscribe(new Observer[String] {
    override def onNext(m: String) = log(s"Movies Watchlist - $m")
    override def onError(e: Throwable) = log(s"Ooops - $e!")
    override def onCompleted() = log(s"No more movies.")
  })
}

object ObservablesCreate extends App {
  import rx.lang.scala._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesCreate)
  def log(s: String) = logger.info(s)

  val vms = Observable.create[String] { obs =>
    obs.onNext("JVM")
    obs.onNext(".NET")
    obs.onNext("DartVM")
    obs.onCompleted()
    Subscription()
  }

  log(s"About to subscribe")
  vms.subscribe(log _, e => log(s"oops - $e"), () => log("Done!"))
  log(s"Subscription returned")

}

object ObservablesCreateFuture extends App {
  import rx.lang.scala._
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesCreateFuture)
  def log(s: String) = logger.info(s)

  val f = Future {
    Thread.sleep(500)
    "Back to the Future(s)"
  }

  val o = Observable.create[String] { obs =>
    f foreach {
      case s =>
        obs.onNext(s)
        obs.onCompleted()
    }
    f.failed foreach {
      case t => obs.onError(t)
    }
    Subscription()
  }

  o.subscribe(log _, e => log(s"oops - $e"), () => log("Done!"))

  Thread.sleep(1000)

}

object ObservablesFromFuture extends App {
  import rx.lang.scala._
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesFromFuture)
  def log(s: String) = logger.info(s)

  val o = Observable.from(Future {
    Thread.sleep(500)
    "Back to the Future(s)"
  })

   o.subscribe(log _, e => log(s"oops - $e"), () => log("Done!"))
  
  Thread.sleep(1000)
}

object ObservablesCombinators extends App {
  import rx.lang.scala._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesCombinators)
  def log(s: String) = logger.info(s)

  val roles = Observable.items("The Good", "The Bad", "The Ugly")
  val names = Observable.items("Clint Eastwood", "Lee Van Cleef", "Eli Wallach")
  val zipped = names.zip(roles).map { case (name, role) => s"$name - $role" }

  zipped.subscribe(log _, e => log(s"oops - $e"), () => log("Done!"))

}

object ObservablesSubscriptions extends App {
  import rx.lang.scala._
  import org.apache.commons.io.monitor._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesSubscriptions)
  def log(s: String) = logger.info(s)

  def modifiedFiles(directory: String): Observable[String] = {
    Observable.create { observer =>
      val fileMonitor = new FileAlterationMonitor(1000)
      val fileObs = new FileAlterationObserver(directory)
      val fileLis = new FileAlterationListenerAdaptor {
        override def onFileChange(file: java.io.File) {
          observer.onNext(file.getName)
        }
      }
      fileObs.addListener(fileLis)
      fileMonitor.addObserver(fileObs)
      fileMonitor.start()

      Subscription { fileMonitor.stop() }
    }
  }

  log(s"starting to monitor files")
  val subscription = modifiedFiles(".").subscribe(filename => log(s"$filename modified!"))
  log(s"please modify and save a file")

  Thread.sleep(10000)

  subscription.unsubscribe()
  log(s"monitoring done")

}

object ObservablesHot extends App {
  import rx.lang.scala._
  import org.apache.commons.io.monitor._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesHot)
  def log(s: String) = logger.info(s)

  val fileMonitor = new FileAlterationMonitor(1000)
  fileMonitor.start()

  def modifiedFiles(directory: String): Observable[String] = {
    val fileObs = new FileAlterationObserver(directory)
    fileMonitor.addObserver(fileObs)
    Observable.create { observer =>
      val fileLis = new FileAlterationListenerAdaptor {
        override def onFileChange(file: java.io.File) {
          observer.onNext(file.getName)
        }
      }
      fileObs.addListener(fileLis)

      Subscription { fileObs.removeListener(fileLis) }
    }
  }

  log(s"first subscribe call")
  val subscription1 = modifiedFiles(".").subscribe(filename => log(s"$filename modified!"))

  Thread.sleep(6000)

  log(s"another subscribe call")
  val subscription2 = modifiedFiles(".").subscribe(filename => log(s"$filename changed!"))

  Thread.sleep(6000)

  log(s"unsubscribed second call")
  subscription2.unsubscribe()

  Thread.sleep(6000)

  fileMonitor.stop()

}

object ObservablesHotVsCold extends App {
  import java.util.{ Timer, TimerTask }
  import scala.collection._
  import rx.lang.scala._
  import org.apache.logging.log4j.LogManager
  import org.apache.logging.log4j.Logger

  val logger = LogManager.getLogger(ObservablesHotVsCold)
  def log(s: String) = logger.info(s)

  val songs = List("Eye of the Tiger", "You Spin Me Round", "Rebel Yell")
  val myPlaylist = Observable.from(songs)

  object Player extends TimerTask {
    val timer = new Timer
    var index = 0
    var subscribers = mutable.Set[Subscriber[String]]()
    def start() = timer.schedule(this, 0L, 1000L)
    def stop() = timer.cancel()

    def run() {
      index = (index + 1) % songs.length
      Player.synchronized { for (s <- subscribers) s.onNext(songs(index)) }
    }
    def turnOn(s: Subscriber[String]) = Player.synchronized { subscribers += s }
    def turnOff(s: Subscriber[String]) = Player.synchronized { subscribers -= s }
  }
  Player.start()

  val currentlyPlaying = Observable[String] { subscriber =>
    Player.turnOn(subscriber)
    subscriber.add(Subscription { Player.turnOff(subscriber) })
  }

  log(s"adding to a cold observable")
  myPlaylist.subscribe(log _)
  log(s"adding to a cold observable again")
  myPlaylist.subscribe(log _)
  Thread.sleep(2000)

  log(s"adding to a hot observable")
  val subscription1 = currentlyPlaying.subscribe(log _)
  Thread.sleep(2400)
  subscription1.unsubscribe()
  Thread.sleep(1200)
  log(s"adding to a hot observable again")
  val subscription2 = currentlyPlaying.subscribe(log _)
  Thread.sleep(2400)
  subscription2.unsubscribe()
  Thread.sleep(1200)
  log(s"Done -- shutting down the Player")
  Player.stop()

}
