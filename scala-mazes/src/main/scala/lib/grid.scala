package lib
import java.awt.image.BufferedImage
import java.awt.Color

trait TextRenderer[T] {
  def contentsOf(cell: T): String = " "
}

trait ImageRenderer[T] {
  def toPng(cellSize: Int = 10): BufferedImage
  def backgroundColorFor(cell: T): Color = null
}

/**
  * Defines grid contract
  */
trait Grid {
  type CellType <: MazeCell

  var size: (Int, Int)
  def numCells: Int
  def getCell(row: Int, column: Int): CellType
  def randomCell(): CellType
  def eachRow(fn: (Iterator[CellType] => Unit))
  def eachCell(fn: (CellType => Unit)): Unit
}

