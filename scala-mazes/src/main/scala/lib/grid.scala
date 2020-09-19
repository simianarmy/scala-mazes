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
trait Grid extends TextRenderer with ImageRenderer {
  val size: (Int, Int)
  def numCells: Int
  def getCell(row: Int, column: Int): MazeCell
  def randomCell(): MazeCell
  def eachRow(fn: (Iterator[MazeCell] => Unit))
  def eachCell(fn: (MazeCell => Unit)): Unit
}

