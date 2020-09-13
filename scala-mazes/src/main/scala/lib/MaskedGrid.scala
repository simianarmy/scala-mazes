package lib

import lib.OrthogonalGrid
import lib.Cell
import lib.Mask

class MaskedGrid(val mask: Mask) extends OrthogonalGrid(mask.rows, mask.columns) {

  def prepareGrid(): Unit = {
    for (i <- 0 until rows; j <- 0 until columns) {
      if (mask(i)(j)) {
        _grid(i)(j) = new Cell(i, j);
      }
    }
  }

  override def randomCell(): Cell = {
    val (row, col) = mask.randomLocation()
    getCell(row, col)
  }

  override def numCells(): Int = mask.count()
}
