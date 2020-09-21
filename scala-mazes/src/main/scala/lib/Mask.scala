package lib

import java.io.File
import scala.io.Source
import javax.imageio.ImageIO
import java.awt.Color
import scala.util.{Try,Success,Failure}

object Mask {
  def fromTxt(file: String): Try[Mask] = Try {
    val bufferedSource = Source.fromFile(file)
    var lines = Array[String]()

    try {
      lines = bufferedSource.getLines.filter(_.length() > 0).toArray
    } finally {
      bufferedSource.close
    }

    val rows = lines.length
    val columns = lines(0).length
    var mask = new Mask(rows, columns)

    for (i <- 0 until rows; j <- 0 until columns) {
      mask(i)(j) = lines(i)(j) != 'X'
    }

    mask
  }

  def fromPng(file: String): Try[Mask] = Try {
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

class Mask(val rows: Int, val columns: Int) extends Randomizer {
  var bits = Array.ofDim[Boolean](rows, columns)

  for (i <- 0 until rows; j <- 0 until columns) {
    bits(i)(j) = true
  }

  def apply(i: Int): Array[Boolean] = {
    return bits(i)
  }

  def count(): Int = {
    (for {
      i <- 0 until rows; j <- 0 until columns
      bit = if (bits(i)(j)) 1 else 0
    } yield bit).sum
  }

  def randomLocation(): (Int, Int) = {
    def lookup(): (Int, Int) = {
      val row = rand.nextInt(rows)
      val col = rand.nextInt(columns)
      if (bits(row)(col)) (row, col) else lookup()
    }
    if (count() > 0) lookup() else (-1, -1)
  }
}

