package lib

import scala.collection.mutable.ArrayBuffer

object GridCell {
  def nullCell = new GridCell(-1, -1)
}

class GridCell(row: Int, column: Int) extends MazeCell(row, column) {
  var north: GridCell = null;
  var south: GridCell = null;
  var east: GridCell = null;
  var west: GridCell = null;

  def neighbors: List[MazeCell] = {
    for (dir <- List(north, south, east, west) if dir != null) yield dir
  }

  override def toString: String =
    s"[GridCell: " + row + ", " + column + "]";
}
