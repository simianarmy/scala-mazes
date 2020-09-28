package lib

class DistanceGrid(rows: Int, columns: Int) extends OrthogonalGrid[GridCell](rows, columns) {
  override def id: String = "di"

  override def contentsOf(cell: MazeCell): String = {
    if (distances != null && distances.get(cell) >= 0) {
      java.lang.Long.toString(distances.get(cell), 36)
    } else {
      " "
    }
  }
}
