package lib

import lib.OrthogonalGrid
import lib.Mask

class MaskedGrid(val mask: Mask) extends OrthogonalGrid(mask.rows, mask.columns) {

  def prepareGrid(): Unit = {
    for (i <- 0 until rows; j <- 0 until columns) {
      if (mask(i)(j)) {
        _grid(i)(j) = new GridCell(i, j);
      }
    }
  }

  override def randomCell(): GridCell = {
    val (row, col) = mask.randomLocation()
    getCell(row, col)
  }

  override def numCells(): Int = mask.count()
}
