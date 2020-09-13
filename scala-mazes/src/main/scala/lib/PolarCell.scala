package lib

import scala.collection.mutable.ArrayBuffer

case class PolarCell(row: Int, column: Int) extends MazeCell {
  type CT = PolarCell

  var cw: PolarCell = null
  var ccw: PolarCell = null
  var inward: PolarCell = null
  private var _outward = new ArrayBuffer[PolarCell]()

  def outward = _outward

  def link(cell: CT, bidi: Boolean = true): CT = {
    links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    cell
  }

  def unlink(cell: CT, bidi: Boolean = true): CT = {
    links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    cell
  }

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
