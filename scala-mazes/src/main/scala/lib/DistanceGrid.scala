package lib

class DistanceGrid(rows: Int, columns: Int) extends OrthogonalGrid(rows, columns) {
  var _distances: Distances[MazeCell] = null

  def distances: Distances[MazeCell] = _distances

  def distances_=(d: Distances[MazeCell]): Unit = {
    _distances = d
  }

  override def contentsOf(cell: MazeCell): String = {
    if (distances != null && distances.get(cell) >= 0) {
      java.lang.Long.toString(distances.get(cell), 36)
    } else {
      null
    }
  }
}
