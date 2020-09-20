package lib

import scala.collection.mutable.ArrayBuffer

class PolarCell(row: Int, column: Int) extends MazeCell(row, column) {
  var cw: PolarCell = null
  var ccw: PolarCell = null
  var inward: PolarCell = null
  private var _outward = new ArrayBuffer[PolarCell]()

  def outward = _outward

  def neighbors: List[MazeCell] = {
    (for (cell <- List(cw, ccw, inward) if cell != null) yield cell) ++ outward
  }

  override def toString: String =
    s"[PolarCell: " + row + ", " + column + "]";
}
