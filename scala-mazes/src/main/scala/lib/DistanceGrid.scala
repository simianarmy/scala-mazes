package lib

class DistanceGrid(rows: Int, columns: Int) extends OrthogonalGrid(rows, columns) {
  var _distances: Distances[GridCell] = null

  def distances: Distances[GridCell] = _distances

  def distances_=(d: Distances[GridCell]): Unit = {
    _distances = d
  }

  override def contentsOf(cell: GridCell): String = {
    if (distances != null && distances.get(cell) >= 0) {
      java.lang.Long.toString(distances.get(cell), 36)
    } else {
      null
    }
  }
}
