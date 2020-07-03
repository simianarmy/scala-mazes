/**
  * The mighty Cell class
  */
//package mazes
import scala.collection.mutable.ArrayBuffer

class Cell(var row: Int, var column: Int) {
  var north: Cell = null;
  var south: Cell = null;
  var east: Cell = null;
  var west: Cell = null;
  var links = scala.collection.mutable.Map[Cell, Boolean]();

  def link(cell: Cell, bidi: Boolean = true): Cell = {
    this.links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    return this;
  }

  def unlink(cell: Cell, bidi: Boolean = true): Cell = {
    this.links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    return this;
  }

  def getLinks(): Iterable[Cell] = {
    return this.links.keys;
  }

  def isLinked(cell: Cell): Boolean = {
    return this.links.contains(cell);
  }

  def neighbors(): ArrayBuffer[Cell] = {
    var list: ArrayBuffer[Cell] = new ArrayBuffer();

    if (this.north != null) list += this.north;
    if (this.south != null) list += this.south;
    if (this.east != null) list += this.east;
    if (this.west != null) list += this.west;

    return list;
  }

  override def toString: String =
    s"[Cell: " + row + ", " + column + "]";
}
