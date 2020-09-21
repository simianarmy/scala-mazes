package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, OrthogonalGrid, MazeCell, GridCell, Randomizer}

class Sidewinder extends GeneralGenerator with Randomizer {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    var run = new ArrayBuffer[MazeCell]()

    grid.eachRow(itr => {
      run.clear();

      while (itr.hasNext) {
        val cell = itr.next()
        run += cell

        grid match {
          case OrthogonalGrid(_,_) => {
            val gc = cell.asInstanceOf[GridCell]
            val atEasternBoundary = (gc.east == null);
            val atNorthernBoundary = (gc.north == null);
            val shouldCloseOut =
              atEasternBoundary || (!atNorthernBoundary && rand.nextInt(2) == 0);

            if (shouldCloseOut) {
              var member = run(rand.nextInt(run.length)).asInstanceOf[GridCell];

              if (member.north != null) {
                member.link(member.north);
              }
              run.clear();
            } else {
              cell.link(gc.east);
            }
          }

          case _ => ()
        }
      }
    });

    grid
  }
  override def toString: String = "Sidewinder"
}
