package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, OrthogonalGrid, MazeCell, GridCell, Randomizer, HexGrid}

class Sidewinder extends GeneralGenerator with Randomizer {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    def run() = {
      var buffer = new ArrayBuffer[MazeCell]()

      grid.eachRow(itr => {
        buffer.clear();

        while (itr.hasNext) {
          val cell = itr.next()
          buffer += cell

          val east = grid.getCell(cell.row, cell.column + 1)
          val gc = cell.asInstanceOf[GridCell]
          val atEasternBoundary = (east == null);
          val atNorthernBoundary = (gc.north == null);
          val shouldCloseOut =
            atEasternBoundary || (!atNorthernBoundary && rand.nextInt(2) == 0);

          if (shouldCloseOut) {
            var member = buffer(rand.nextInt(buffer.length)).asInstanceOf[GridCell];

            if (member.north != null) {
              member.link(member.north);
            }
            buffer.clear();
          } else {
            cell.link(east);
          }
        }
      })
      grid
    }

    grid match {
      case OrthogonalGrid(_,_) => run()
      case HexGrid(_,_) => run()
      case _ => grid
    }
  }

  override def toString: String = "Sidewinder"
}
