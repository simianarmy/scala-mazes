package lib

import lib.OrthogonalGrid
import lib.Mask

class MaskedGrid(val mask: Mask) extends OrthogonalGrid(mask.rows, mask.columns) {

  override def id: String = "ma"

  override def prepareGrid(): Array[Array[GridCell]] = {
    var cells = Array.ofDim[GridCell](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      if (mask(i)(j)) {
        cells(i)(j) = new GridCell(i, j);
      }
    }

    cells
  }

  override def randomCell(): GridCell = {
    val (row, col) = mask.randomLocation()
    getCell(row, col)
  }

  override def numCells: Int = mask.count()
}
