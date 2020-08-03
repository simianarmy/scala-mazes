package lib

class Mask {
  var bits: Array[Array[Boolean]] = null
  var rows = 0
  var columns = 0
  val rand = new scala.util.Random(System.currentTimeMillis)

  def this(rows: Int, columns: Int) = {
    this()

    this.rows = rows
    this.columns = columns

    // initialize mask indicating which cells are on
    bits = Array.ofDim[Boolean](rows, columns)

    for (i <- 0 until rows; j <- 0 until columns) {
      bits(i)(j) = true
    }
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
