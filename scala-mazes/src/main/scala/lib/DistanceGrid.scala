package lib

class DistanceGrid(rows: Int, columns: Int) extends OrthogonalGrid(rows, columns) {
  protected var _distances: Distances = null

  def distances: Distances = _distances

  def distances_=(d: Distances): Unit = {
    _distances = d
  }

  override def contentsOf(cell: Cell): String = {
    if (distances != null && distances.get(cell) >= 0) {
      java.lang.Long.toString(distances.get(cell), 36)
    } else {
      null
    }
  }
}
