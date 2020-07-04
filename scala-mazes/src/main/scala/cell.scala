/**
  * The mighty Cell class
  */
//package mazes
import scala.collection.mutable.{ArrayBuffer, Map}

class Cell(var row: Int, var column: Int) {
  var north: Cell = null;
  var south: Cell = null;
  var east: Cell = null;
  var west: Cell = null;
  var links = Map[Cell, Boolean]();

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
    var list = new ArrayBuffer[Cell]();

    if (this.north != null) list += this.north;
    if (this.south != null) list += this.south;
    if (this.east != null) list += this.east;
    if (this.west != null) list += this.west;

    return list;
  }

  def distances(): Distances = {
    var distances = new Distances(this)
    var frontier = new ArrayBuffer[Cell](10)
    var newFrontier = new ArrayBuffer[Cell](10)

    frontier += this

    while (!frontier.isEmpty) {
      newFrontier.clear()

      for (i <- 0 until frontier.length) {
        val cell = frontier(i)
        cell
          .getLinks()
          .foreach(linked => {
            if (distances.get(linked) == Distances.NotFound) {
              distances.set(linked, distances.get(cell) + 1)
              newFrontier += linked
            }
          })
      }

      frontier = newFrontier.clone()
    }
    distances
  }

  override def toString: String =
    s"[Cell: " + row + ", " + column + "]";
}
