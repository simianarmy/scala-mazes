package lib

import scala.collection.mutable.ArrayBuffer

object RandomUtil {
  val r = new scala.util.Random(System.currentTimeMillis)

  def sample[T](seq: ArrayBuffer[T]): T = {
    seq(r.nextInt(seq.length))
  }

  def sample[T](seq: Array[T]): T = {
    seq(r.nextInt(seq.length))
  }

  def sample[T](seq: List[T]): T = {
    seq(r.nextInt(seq.length))
  }
}
