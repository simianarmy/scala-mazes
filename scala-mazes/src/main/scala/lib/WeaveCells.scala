package lib

class OverCell(row: Int, column: Int, var grid: WeaveGrid) extends GridCell(row, column) {
  override def neighbors: List[MazeCell] = {
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

  private def getSharedNeighbor(cell: MazeCell): Option[MazeCell] = {
    if (!north.isNil && north == cell.south) Some(north)
    else if (!south.isNil && south == cell.north) Some(south)
    else if (!east.isNil && east == cell.west) Some(east)
    else if (!west.isNil && west == cell.east) Some(west)
    else None
  }

  override def link[A <: MazeCell](cell: A): Unit = {
    cell match {
      case c: GridCell =>
        getSharedNeighbor(c).orNull match {
          case c: OverCell if !c.isNil => grid.tunnelUnder(c)
          case _ => super.link(cell)
        }
      case _ => ()
    }
  }

  override def linkBidirectional[A <: MazeCell](cell: A): Unit = {
    cell match {
      case c: GridCell =>
        getSharedNeighbor(c).orNull match {
          case c: OverCell if !c.isNil => grid.tunnelUnder(c)
          case _ => super.linkBidirectional(cell)
        }
      case _ => ()
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

class UnderCell(overCell: OverCell) extends OverCell(overCell.row, overCell.column, overCell.grid) {
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
