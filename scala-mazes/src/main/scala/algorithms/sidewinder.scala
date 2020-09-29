package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, OrthogonalGrid, MazeCell, GridCell, Randomizer, HexGrid, TriangleGrid}

class Sidewinder extends GeneralGenerator with Randomizer {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    def run() = {
      var buffer = new ArrayBuffer[MazeCell]()

      grid.eachRow(itr => {
        buffer.clear();

        while (itr.hasNext) {
          val cell = itr.next()
          buffer += cell

          val east = grid.getCell(cell.row, cell.column + 1) match {
            case Some(cell) => cell
            case _ => MazeCell.nilCell[GridCell]
          }
          val gc = cell.asInstanceOf[GridCell]
          val atEasternBoundary = east.isNil
          val atNorthernBoundary = gc.north == null
          val shouldCloseOut =
            atEasternBoundary || (!atNorthernBoundary && rand.nextInt(2) == 0);

          if (shouldCloseOut) {
            var member = buffer(rand.nextInt(buffer.length)).asInstanceOf[GridCell];

            if (member.north != null) {
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

    grid match {
      case OrthogonalGrid(_,_) => run()
      case _ => grid
    }
  }

  override def toString: String = "Sidewinder"
}
