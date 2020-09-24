package lib

class OverCell(row: Int, column: Int, grid: Grid[OverCell]) extends GridCell(row, column) {
  override def neighbors: List[GridCell] = {
    var list = super.neighbors

    if (canTunnelNorth) {
      list ::= north.north
    }
    if (canTunnelSouth) {
      list ::= south.south
    }
    if (canTunnelEast) {
      list ::= east.east
    }
    if (canTunnelWest) {
      list ::= west.west
    }

    list
  }


  // FIXME
  override def link[A <: GridCell](cell: A, bidi: Boolean = true): A = {
    if (north != null && north == cell.south) north
    else south
  }

  def canTunnelNorth: Boolean = {
    north != null && north.north != null && north.asInstanceOf[OverCell].isHorizontalPassage
  }

  def canTunnelSouth: Boolean = {
    south != null && south.south != null && south.asInstanceOf[OverCell].isHorizontalPassage
  }

  def canTunnelEast: Boolean = {
    east != null && east.east != null && east.asInstanceOf[OverCell].isVerticalPassage
  }

  def canTunnelWest: Boolean = {
    west != null && west.west != null && west.asInstanceOf[OverCell].isVerticalPassage
  }

  def isHorizontalPassage: Boolean = {
    isLinked(east) && isLinked(west) && !isLinked(north) && !isLinked(south)
  }

  def isVerticalPassage: Boolean = {
    isLinked(north) && isLinked(south) && !isLinked(east) && !isLinked(west)
  }
}
