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

class GridIterator[A <: MazeCell](grid: Grid[A]) extends Iterator[A] {
  var position: Int = 0;
  def hasNext(): Boolean = {
    position < grid.size()
  }
  def next(): A = {
    val el = grid.cellAt(position)
    position += 1
    el
  }
}

/**
  * Defines grid contract
  */
abstract class Grid[A <: MazeCell](val rows: Int, val columns: Int) extends TextRenderer with ImageRenderer {
  val dimensions = (rows, columns)
  def size(): Int = rows * columns
  val r = scala.util.Random
  def numCells: Int
  def getCell(row: Int, column: Int): A
  def cellAt(index: Int): A
  def randomCell(): A

  def iterator(): GridIterator[A] = {
    new GridIterator(this)
  }

  def eachRow(fn: (Iterator[A] => Unit)): Unit ={
    // collect cells into rows
    for (row <- iterator().grouped(columns)) {
      fn(row.iterator);
    }
  }

  def eachCell(fn: (A => Unit)): Unit = {
    val itr = iterator()
    while (itr.hasNext()) {
      fn(itr.next())
    }
  }
}

