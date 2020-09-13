package lib

class DistanceGrid(rows: Int, columns: Int) extends OrthogonalGrid(rows, columns) {
  protected var _distances: Distances[CellType]

  def distances: Distances[CellType] = _distances

  def distances_=(d: Distances[CellType]): Unit = {
    _distances = d
  }

  def contentsOf(cell: CellType): String = {
    if (distances != null && distances.get(cell) >= 0) {
      java.lang.Long.toString(distances.get(cell), 36)
    } else {
      null
    }
  }
}
