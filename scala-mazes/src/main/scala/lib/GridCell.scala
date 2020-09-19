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

  def neighbors(): ArrayBuffer[GridCell] = {
    var list = new ArrayBuffer[GridCell]();

    if (this.north != null) list += this.north;
    if (this.south != null) list += this.south;
    if (this.east != null) list += this.east;
    if (this.west != null) list += this.west;

    list;
  }

  override def toString: String =
    s"[GridCell: " + row + ", " + column + "]";
}
