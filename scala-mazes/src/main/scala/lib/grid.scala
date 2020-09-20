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
abstract class Grid[A <: MazeCell, B](rows: Int, columns: Int) extends TextRenderer with ImageRenderer {
  val dimensions = (rows, columns)
  val r = scala.util.Random
  val grid: B
  def numCells: Int
  def getCell(row: Int, column: Int): A
  def randomCell(): A

  def eachCell(fn: (A => Unit)): Unit = {
    for (row <- grid) {
      row.foreach(fn)
    }
  }
}

