package lib

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Common properties to measure distances between cells in a grid
  */
trait CellDistances {
  var _distances: Distances[MazeCell] = null

  def distances: Distances[MazeCell] = _distances

  def distances_=(d: Distances[MazeCell]) = {
    _distances = d
  }
}

trait CellDistancesGenerator {
  def generateDistances(source: MazeCell): Distances[MazeCell] = {
    var distances = new Distances[MazeCell](source)
    var frontier = new ArrayBuffer[MazeCell](10)
    var newFrontier = new ArrayBuffer[MazeCell](10)

    frontier += source

    while (!frontier.isEmpty) {
      newFrontier.clear()

      for (i <- 0 until frontier.length) {
        val cell = frontier(i)

        for (linked <- cell.links) {
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
