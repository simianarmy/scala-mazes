/**
  * The mighty Cell class
  */
package lib

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * TODO: Move to own file
  */
trait CellDistances {
  type CT

  def distances(root: CT): Distances = {
    var distances = new Distances(root)
    var frontier = new ArrayBuffer[CT](10)
    var newFrontier = new ArrayBuffer[CT](10)

    frontier += root

    while (!frontier.isEmpty) {
      newFrontier.clear()

      for (i <- 0 until frontier.length) {
        val cell = frontier(i)

        for (linked <- cell.getLinks()) {
          if (distances.get(linked) == Distances.NotFound) {
            distances.set(linked, distances.get(cell) + 1)
            newFrontier += linked.asInstanceOf[CT]
          }
        }
      }

      frontier = newFrontier.clone()
    }
    distances
  }
}

trait MazeCell extends CellDistances {
  type CT

  var links = Map[CT, Boolean]()
  def getLinks(): Iterable[CT] = links.keys
  def isLinked(cell: CT): Boolean = links.contains(cell)
  def neighbors(): ArrayBuffer[CT]
}

abstract class LinkableCell extends MazeCell {
  type CT

  def link(cell: LinkableCell, bidi: Boolean = true): LinkableCell = {
    links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    cell
  }

  def unlink(cell: LinkableCell, bidi: Boolean = true): LinkableCell = {
    links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    cell
  }
}

