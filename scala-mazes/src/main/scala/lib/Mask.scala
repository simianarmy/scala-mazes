package lib

import scala.io.Source

object Mask {
  def fromTxt(file: String) = {
    val bufferedSource = Source.fromFile(file)

    val lines = bufferedSource.getLines.filter(_.length() > 0).toArray
    val rows = lines.length
    val columns = lines(0).length
    var mask = new Mask(rows, columns)

    for (i <- 0 until rows; j <- 0 until columns) {
      mask(i)(j) = lines(i)(j) != 'X'
    }

    bufferedSource.close

    mask
  }
}

class Mask(val rows: Int, val columns: Int) {
  var bits = Array.ofDim[Boolean](rows, columns)
  val rand = new scala.util.Random(System.currentTimeMillis)

  for (i <- 0 until rows; j <- 0 until columns) {
    bits(i)(j) = true
  }

  def apply(i: Int): Array[Boolean] = {
    return bits(i)
  }

  def count(): Int = {
    var count = 0
    for (i <- 0 until rows; j <- 0 until columns) {
      if (bits(i)(j)) count += 1
    }
    count
  }

  def randomLocation(): (Int, Int) = {
    var loop = true
    var res = (-1, -1)

    while (loop) {
      val row = rand.nextInt(rows)
      val col = rand.nextInt(columns)

      if (bits(row)(col)) {
        res = (row, col)
        loop = false
      }
    }
    res
  }
}

