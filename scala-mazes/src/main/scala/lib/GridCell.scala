package lib

import lib.MazeCell._

class GridCell(row: Int, column: Int) extends MazeCell(row, column) {
  private var _north: Option[GridCell] = None
  private var _south: Option[GridCell] = None
  private var _east: Option[GridCell] = None
  private var _west: Option[GridCell] = None

  def north: GridCell = _north.getOrElse(nilCell[GridCell])
  def south: GridCell = _south.getOrElse(nilCell[GridCell])
  def east: GridCell = _east.getOrElse(nilCell[GridCell])
  def west: GridCell = _west.getOrElse(nilCell[GridCell])

  def north_=(that: GridCell) = _north = Some(that)
  def south_=(that: GridCell) = _south = Some(that)
  def east_=(that: GridCell) = _east = Some(that)
  def west_=(that: GridCell) = _west = Some(that)

  def neighbors: List[GridCell] = {
    List(north, south, east, west).filterNot(p => p.isNil)
  }

  override def toString: String =
    s"[GridCell: " + row + ", " + column + "]";
}
