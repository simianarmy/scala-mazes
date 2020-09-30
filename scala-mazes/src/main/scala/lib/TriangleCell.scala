package lib

class TriangleCell(row: Int, column: Int) extends GridCell(row, column) {
  def isUpright: Boolean = ((row + column) % 2 == 0)

  override def neighbors: List[GridCell] = {
    def cellIf(cell: GridCell, test: => Boolean): List[GridCell] = if (test) List(cell) else Nil

    val list = List(west, east).filterNot(p => p.isNil)

    list ::: cellIf(north, {
      !isUpright && !north.isNil
    }) ::: cellIf(south, {
      isUpright && !south.isNil
    })
  }
}
