package lib

import lib.MazeCell._

class GridCell(row: Int, column: Int) extends MazeCell(row, column) {
  override def north: MazeCell = _north.getOrElse(MazeCell.nilCell[GridCell])
  override def south: MazeCell = _south.getOrElse(MazeCell.nilCell[GridCell])
  override def east: MazeCell = _east.getOrElse(MazeCell.nilCell[GridCell])
  override def west: MazeCell = _west.getOrElse(MazeCell.nilCell[GridCell])

  override def neighbors: List[MazeCell] = {
    List(north, south, east, west).filterNot(p => p.isNil)
  }

  override def toString: String =
    s"[GridCell: " + row + ", " + column + "]";
}
