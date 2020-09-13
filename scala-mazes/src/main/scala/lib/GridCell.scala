package lib

import scala.collection.mutable.ArrayBuffer

class GridCell(var row: Int, var column: Int) extends LinkableCell {
  type CT = GridCell

  var north: GridCell = null;
  var south: GridCell = null;
  var east: GridCell = null;
  var west: GridCell = null;

  override def neighbors(): ArrayBuffer[GridCell] = {
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
