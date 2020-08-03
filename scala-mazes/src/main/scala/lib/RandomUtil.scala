package lib

import scala.collection.mutable.ArrayBuffer

object RandomUtil {
  val r = new scala.util.Random(System.currentTimeMillis)

  def sample[T](seq: ArrayBuffer[T]): T = {
    seq(r.nextInt(seq.length))
  }
}
