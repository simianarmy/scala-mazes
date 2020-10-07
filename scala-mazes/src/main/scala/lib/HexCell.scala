package lib

import lib.MazeCell._

class HexCell(row: Int, column: Int) extends GridCell(row, column) {
  var _northeast: Option[HexCell] = None
  var _northwest: Option[HexCell] = None
  var _southeast: Option[HexCell] = None
  var _southwest: Option[HexCell] = None

  def northeast: HexCell = _northeast.getOrElse(nilCell[HexCell])
  def northwest: HexCell = _northwest.getOrElse(nilCell[HexCell])
  def southeast: HexCell = _southeast.getOrElse(nilCell[HexCell])
  def southwest: HexCell = _southwest.getOrElse(nilCell[HexCell])

  def northeast_=(that: HexCell) = _northeast = Some(that)
  def northwest_=(that: HexCell) = _northwest = Some(that)
  def southeast_=(that: HexCell) = _southeast = Some(that)
  def southwest_=(that: HexCell) = _southwest = Some(that)

  override def neighbors: List[GridCell] = {
    List(northwest, north, northeast, southwest, south, southeast).filterNot(p => p.isNil)
  }
}
