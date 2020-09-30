package lib

import scala.reflect.{ClassTag, classTag}

class MaskedGrid(val mask: Mask) extends OrthogonalGrid[GridCell](mask.rows, mask.columns) {

  override def id: String = "ma"

  override def prepareGrid[A <: GridCell : ClassTag](): Array[Array[A]] = {
    var cells = Array.ofDim[A](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      if (mask(i)(j)) {
        cells(i)(j) = MazeCell.createCell[A](i, j)
      } else {
        cells(i)(j) = MazeCell.nilCell[A]
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
