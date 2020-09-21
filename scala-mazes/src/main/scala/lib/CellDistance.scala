package lib

trait CellDistance {
  var _distances: Distances[MazeCell] = null

  def distances: Distances[MazeCell] = _distances

  def distances_=(d: Distances[MazeCell]): Unit = {
    _distances = d
  }
}
