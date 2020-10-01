package lib

import scala.reflect.{ClassTag, classTag}

class MaskedGrid(val mask: Mask) extends OrthogonalGrid[GridCell](mask.rows, mask.columns) {

  override def id: String = "ma"

  override protected def prepareGrid[A <: GridCell : ClassTag]: Array[Array[A]] = {
    var cells = Array.ofDim[A](rows, columns)

    for (i <- 0 until rows; j <- 0 until columns) {
      cells(i)(j) = if (mask(i)(j)) createCell[A](i, j) else MazeCell.nilCell[A]
    }

    cells
  }

  override def randomCell(): GridCell = {
    val (row, col) = mask.randomLocation()
    getCell(row, col)
  }

  override def numCells: Int = mask.count()
}
