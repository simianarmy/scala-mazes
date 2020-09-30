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
    if (!north.isNil && north == cell.south) north
    else if (!south.isNil && south == cell.north) south
    else if (!east.isNil && east == cell.west) east
    else if (!west.isNil && west == cell.east) west
    else null
  }

  def link(cell: GridCell): Unit = {
    getSharedNeighbor(cell) match {
      case c: OverCell if !c.isNil => grid.tunnelUnder(c)
      case _ => super.link(cell)
    }
  }

  def linkBidirectional(cell: GridCell): Unit = {
    getSharedNeighbor(cell) match {
      case c: OverCell if !c.isNil => grid.tunnelUnder(c)
      case _ => super.linkBidirectional(cell)
    }
  }

  def canTunnelNorth: Boolean = {
    !north.isNil && !north.north.isNil && north.asInstanceOf[OverCell].isHorizontalPassage
  }

  def canTunnelSouth: Boolean = {
    !south.isNil && !south.south.isNil && south.asInstanceOf[OverCell].isHorizontalPassage
  }

  def canTunnelEast: Boolean = {
    !east.isNil && !east.east.isNil && east.asInstanceOf[OverCell].isVerticalPassage
  }

  def canTunnelWest: Boolean = {
    !west.isNil && !west.west.isNil && west.asInstanceOf[OverCell].isVerticalPassage
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

  override def isHorizontalPassage: Boolean = !east.isNil || !west.isNil

  override def isVerticalPassage: Boolean = !north.isNil || !south.isNil
}
