package lib

class PolarCell(row: Int, column: Int) extends Cell(row, column) {
  var cw: Cell = null
  var ccw: Cell = null
  var inward: Cell = null
  private var _outward = new Cells()

  def outward = _outward

  override def neighbors(): Cells = {
    var list = new Cells()

    def addIfNotNull(cell: Cell): Unit = {
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
}
