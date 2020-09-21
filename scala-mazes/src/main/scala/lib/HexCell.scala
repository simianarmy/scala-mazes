package lib

class HexCell(row: Int, column: Int) extends GridCell(row, column) {
  var northeast: HexCell = null
  var northwest: HexCell = null
  var southeast: HexCell = null
  var southwest: HexCell = null

  override def neighbors: List[GridCell] = {
    for {
      cell <- List(northwest, north, northeast, southwest, south, southeast) if cell != null
    } yield cell
  }
}
