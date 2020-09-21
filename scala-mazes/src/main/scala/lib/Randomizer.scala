package lib

trait Randomizer {
  val rand = new scala.util.Random(System.currentTimeMillis)
}

