package lib

import scala.collection.mutable.ArrayBuffer

object GridCell {
  def nullCell = new GridCell(-1, -1)
}

case class GridCell(var row: Int, var column: Int) extends MazeCell {
  var north: GridCell = null;
  var south: GridCell = null;
  var east: GridCell = null;
  var west: GridCell = null;

  // TODO: link and unlink should be in the MazeCell trait
  def link(cell: GridCell, bidi: Boolean = true): GridCell = {
    links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    cell
  }

  def unlink(cell: GridCell, bidi: Boolean = true): GridCell = {
    links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    cell
  }

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

  // TODO: Extract to trait or utility
  def distances(root: GridCell): Distances[GridCell] = {
    var distances = new Distances[GridCell](root)
    var frontier = new ArrayBuffer[GridCell](10)
    var newFrontier = new ArrayBuffer[GridCell](10)

    frontier += root

    while (!frontier.isEmpty) {
      newFrontier.clear()

      for (i <- 0 until frontier.length) {
        val cell = frontier(i)

        for (linked <- cell.getLinks()) {
          val gcLinked = linked.asInstanceOf[GridCell]
          if (distances.get(gcLinked) == Distances.NotFound) {
            distances.set(gcLinked, distances.get(cell) + 1)
            newFrontier += gcLinked
          }
        }
      }

      frontier = newFrontier.clone()
    }
    distances
  }
}