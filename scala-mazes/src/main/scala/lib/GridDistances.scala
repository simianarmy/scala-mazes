package lib

/**
  * Common properties to measure distances between cells in a grid
  */
trait GridDistances {
  var _distances: Distances[MazeCell] = null

  def distances: Distances[MazeCell] = _distances

  def distances_=(d: Distances[MazeCell]) = {
    _distances = d
  }
}

