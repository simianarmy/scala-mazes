package lib
import java.awt.image.BufferedImage
import java.awt.Color

trait TextRenderer {
  def contentsOf(cell: MazeCell): String = " "
}

trait ImageRenderer {
  def toPng(cellSize: Int = 10): BufferedImage
  def backgroundColorFor(cell: MazeCell): Color = null
}

/**
  * Defines grid contract
  */
abstract class Grid[B <: MazeCell, A <: Seq[B]](rows: Int, columns: Int) extends TextRenderer with ImageRenderer {
  val dimensions = (rows, columns)
  val r = scala.util.Random
  val grid: Array[A] // this ok?
  def numCells: Int
  def getCell(row: Int, column: Int): B
  def randomCell(): B

  def eachCell(fn: (B => Unit)): Unit = {
    for (row <- grid) {
      row.foreach(fn)
    }
  }
}

