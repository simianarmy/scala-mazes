package lib

import java.io.File
import scala.io.Source
import javax.imageio.ImageIO
import java.awt.Color
import scala.util.{Try,Success,Failure}

object Mask {
  def fromTxt(file: String): Try[Mask] = Try {
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

  def fromPng(file: String) = {
    val image = ImageIO.read(new File(file))
    val w = image.getWidth
    val h = image.getHeight
    var mask = new Mask(w, h)

    for (x <- 0 until w; y <- 0 until h) {
      val color = new Color(image.getRGB(x, y))
      mask(x)(y) = (color != Color.black)
    }

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

