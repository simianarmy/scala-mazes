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
abstract class Grid[B, A <: Iterable[B]](rows: Int, columns: Int) extends Iterable[A] with TextRenderer with ImageRenderer {
  val dimensions = (rows, columns)
  val r = scala.util.Random
  val grid: Array[A] // this ok?
  def numCells: Int
  def getCell(row: Int, column: Int): MazeCell
  def getRows(): Iterable[A]
  def randomCell(): MazeCell
  def iterable = grid.iterator

  def eachCell(fn: (B => Unit)): Unit = {
    for (row <- grid; cell <- row) {
      fn(cell)
    }
  }
}

