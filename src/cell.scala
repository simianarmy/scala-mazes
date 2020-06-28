/**
 * The mighty Cell class
 */
package mazes

class Cell(var row: Int, var column: Int) {
  var north : Cell = null;
  var south : Cell = null;
  var east : Cell = null;
  var west : Cell = null;
  var links = scala.collection.mutable.Map[Cell, Boolean]();

  def link(cell: Cell, bidi: Boolean = true) : Cell = {
    this.links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    return this;
  }

  def unlink(cell: Cell, bidi: Boolean = true) : Cell = {
    this.links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    return this;
  }

  def getLinks() : Iterable[Cell] = {
    return this.links.keys;
  }

  def isLinked(cell: Cell) : Boolean = {
    return this.links.contains(cell);
  }

  def neighbors() : Array[Cell] = {
    var list : Array[Cell] = new Array(4);

    if (this.north != null) list( 0 ) = this.north;
    if (this.south != null) list( 1 ) = this.south;
    if (this.east != null) list( 2 ) = this.east;
    if (this.west != null) list( 3 ) = this.west;

    return list;
  }

  override def toString : String =
    s"[Cell: " + row + ", " + column + "]";
}
