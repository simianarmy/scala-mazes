package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, OrthogonalGrid, MazeCell, Randomizer, HexGrid, TriangleGrid}

class Sidewinder extends MazeGenerator with Randomizer {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    var buffer = new ArrayBuffer[MazeCell]()

    grid.eachRow(itr => {
      buffer.clear()

      while (itr.hasNext) {
        val cell = itr.next()
        buffer += cell

        val east = grid.getCell(cell.row, cell.column + 1)
        val atEasternBoundary = east.isNil
        val atNorthernBoundary = cell.north.isNil
        val shouldCloseOut =
          atEasternBoundary || (!atNorthernBoundary && rand.nextInt(2) == 0)

        if (shouldCloseOut) {
          var member = buffer(rand.nextInt(buffer.length))

          if (!member.north.isNil) {
            member.linkBidirectional(member.north);
          }
          buffer.clear();
        } else {
          cell.linkBidirectional(east);
        }
      }
    })
    grid
  }

  override def toString: String = "Sidewinder"
}
