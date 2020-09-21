package lib

import scala.collection.mutable.{ArrayBuffer, Map}

//TODO: Make a Trait?
class CellDistanceFinder {
  def distances(sourceCell: MazeCell): Distances[MazeCell] = {
    var distances = new Distances[MazeCell](sourceCell)
    var frontier = new ArrayBuffer[MazeCell](10)
    var newFrontier = new ArrayBuffer[MazeCell](10)

    frontier += sourceCell

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

