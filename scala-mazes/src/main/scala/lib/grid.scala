package lib
import java.awt.image.BufferedImage
import java.awt.Color

trait TextRenderer {
  def contentsOf(cell: Cell): String = " "
}

trait ImageRenderer {
  def toPng(cellSize: Int = 10): BufferedImage
  def backgroundColorFor(cell: Cell): Color = null
}

/**
  * Defines grid contract
  */
trait Grid extends TextRenderer with ImageRenderer {
  type CellType <: Cell

  var size: (Int, Int)
  def numCells: Int
  def getCell(row: Int, column: Int): CellType
  def randomCell(): CellType
  def eachRow(fn: (Iterator[CellType] => Unit))
  def eachCell(fn: (CellType => Unit)): Unit
}
