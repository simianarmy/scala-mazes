class DistanceGrid(rows: Int, columns: Int) extends Grid(rows, columns) {
  var distances: Distances = null

  override def contentsOf(cell: Cell): String = {
    if (distances != null && distances.get(cell) >= 0) {
      java.lang.Long.toString(distances.get(cell), 36)
    } else {
      super.contentsOf(cell)
    }
  }
}
