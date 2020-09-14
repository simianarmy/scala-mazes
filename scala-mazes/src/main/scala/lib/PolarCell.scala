package lib

import scala.collection.mutable.ArrayBuffer

case class PolarCell(var row: Int, var column: Int) extends MazeCell {
  var cw: PolarCell = null
  var ccw: PolarCell = null
  var inward: PolarCell = null
  private var _outward = new ArrayBuffer[PolarCell]()

  def outward = _outward

  def neighbors(): ArrayBuffer[PolarCell] = {
    var list = new ArrayBuffer[PolarCell]()

    def addIfNotNull(cell: PolarCell): Unit = {
      if (cell != null) {
        list += cell
      }
    }

    addIfNotNull(cw)
    addIfNotNull(ccw)
    addIfNotNull(inward)

    list ++= outward
    list
  }

  override def toString: String =
    s"[PolarCell: " + row + ", " + column + "]";
}
