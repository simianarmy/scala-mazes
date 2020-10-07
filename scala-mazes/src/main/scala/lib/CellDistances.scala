package lib

import scala.collection.mutable.{ArrayBuffer, Map}

trait CellDistances {
  this: MazeCell =>

  var weight: Int = 0

  /**
    * Generate all distances from this cell to every other in the grid
    *
    * @return {Distances}
    */
  def distances: Distances[MazeCell] = {
    var distances = new Distances[MazeCell](this)
    var frontier = new ArrayBuffer[MazeCell](10)
    var newFrontier = new ArrayBuffer[MazeCell](10)

    frontier += this

    while (frontier.nonEmpty) {
      newFrontier.clear()

      for (cell <- frontier; linked <- cell.links) {
        if (distances.get(linked) == Distances.NotFound) {
          distances.set(linked, distances.get(cell) + 1)
          newFrontier += linked
        }
      }

      frontier = newFrontier.clone()
    }
    distances
  }
}
