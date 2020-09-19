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
abstract class Grid(rows: Int, columns: Int) extends TextRenderer with ImageRenderer {
  val size = (rows, columns)
  val r = scala.util.Random
  def numCells: Int
  def getCell(row: Int, column: Int): MazeCell
  def randomCell(): MazeCell
  def eachRow(fn: (Iterator[MazeCell] => Unit))

  // TODO: Use iterator pattern
  def eachCell(fn: (MazeCell => Unit)) = {
    eachRow((row: Iterator[MazeCell]) => {
      row.foreach(fn)
    })
  }

}

