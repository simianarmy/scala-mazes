package lib

import scala.collection.mutable.{ArrayBuffer, Map}

abstract class Cell(val row: Int, val column: Int)

abstract class MazeCell(row: Int, column: Int) extends Cell(row, column) {
  var links = Map[MazeCell, Boolean]()

  def getLinks(): Iterable[MazeCell] = links.keys
  def isLinked(cell: MazeCell): Boolean = links.contains(cell)
  def neighbors: List[MazeCell]

  // howowowowowowowowowow????????
  //def link(cell: MazeCell, bidi: Boolean = true): MazeCell
  //def unlink(cell: MazeCell, bidi: Boolean = true): MazeCell
  // TODO: link and unlink should be in the MazeCell trait
  def link(cell: MazeCell, bidi: Boolean = true): MazeCell = {
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

  // TODO: Extract to trait or utility
  def distances: Distances[MazeCell] = {
    var distances = new Distances[MazeCell](this)
    var frontier = new ArrayBuffer[MazeCell](10)
    var newFrontier = new ArrayBuffer[MazeCell](10)

    frontier += this

    while (!frontier.isEmpty) {
      newFrontier.clear()

      for (i <- 0 until frontier.length) {
        val cell = frontier(i)

        for (linked <- cell.getLinks()) {
          val gcLinked = linked
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

