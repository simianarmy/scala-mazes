package lib

class OverCell(row: Int, column: Int, grid: WeaveGrid) extends GridCell(row, column) {
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

  private def getSharedNeighbor(cell: GridCell): GridCell = {
    if (north != null && north == cell.south) north
    else if (south != null && south == cell.north) south
    else if (east != null && east == cell.west) east
    else if (west != null && west == cell.east) west
    else null
  }

  def link(cell: GridCell): Unit = {
    getSharedNeighbor(cell) match {
      case c: OverCell if c != null => grid.tunnelUnder(c)
      case _ => super.link(cell)
    }
  }

  def linkBidirectional(cell: GridCell): Unit = {
    getSharedNeighbor(cell) match {
      case c: OverCell if c != null => grid.tunnelUnder(c)
      case _ => super.linkBidirectional(cell)
    }
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

class UnderCell(overCell: OverCell, grid: WeaveGrid) extends OverCell(overCell.row, overCell.column, grid) {
  if (overCell.isHorizontalPassage) {
    north = overCell.north
    overCell.north.south = this
    south = overCell.south
    overCell.south.north = this

    linkBidirectional(north)
    linkBidirectional(south)
  } else {
    east = overCell.east
    overCell.east.west = this
    west = overCell.west
    overCell.west.east = this

    linkBidirectional(east)
    linkBidirectional(west)
  }

  override def isHorizontalPassage: Boolean = east != null || west != null

  override def isVerticalPassage: Boolean = north != null || south != null
}
