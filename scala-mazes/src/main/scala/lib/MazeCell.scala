package lib

import scala.collection.mutable.{ArrayBuffer, Map}

abstract class Cell(val row: Int, val column: Int)
case class NilCell() extends Cell(-1, -1)

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

  def link[A <: MazeCell](cell: A): Unit = {
    links += (cell -> true);
  }

  def linkBidirectional[A <: MazeCell](cell: A): Unit = {
    link(cell)
    cell.link(this)
  }

  def unlink(cell: MazeCell): Unit = {
    links -= (cell);
  }

  def unlinkBidirectional(cell: MazeCell): Unit = {
    unlink(cell)
    cell.unlink(this)
  }
}

