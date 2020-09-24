package lib

import scala.collection.mutable.{ArrayBuffer, Map}

abstract class Cell(val row: Int, val column: Int)

abstract class MazeCell(row: Int, column: Int) extends Cell(row, column) with Ordered[MazeCell] {
  var weight: Int = 0
  var links = Map[MazeCell, Boolean]()

  def getLinks(): Iterable[MazeCell] = links.keys
  def isLinked(cell: MazeCell): Boolean = links.contains(cell)
  def neighbors: List[MazeCell]
  def distances(): Distances[MazeCell] = {
    new CellDistanceFinder().distances(this)
  }
  def compare(that: MazeCell) = weight compare that.weight


  def link[A <: MazeCell](cell: A, bidi: Boolean = true): A = {
    links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    cell
  }

  def unlink(cell: MazeCell, bidi: Boolean = true): MazeCell = {
    links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    cell
  }
}

