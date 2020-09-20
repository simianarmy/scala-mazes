package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, OrthogonalGrid, GridCell, MazeCell}

object Sidewinder {
  def on[A <: GridCell, B <: Seq[A]](grid: Grid[A,B]): Grid[A,B] = {
    val r = scala.util.Random
    var run = new ArrayBuffer[MazeCell]()

    for (row <- grid.grid) {
      run.clear();

      row.foreach(cell => {
        run += cell;

        grid match {
          case OrthogonalGrid(_,_) => {
            val gc = cell.asInstanceOf[GridCell]
            val atEasternBoundary = (gc.east == null);
            val atNorthernBoundary = (gc.north == null);
            val shouldCloseOut =
              atEasternBoundary || (!atNorthernBoundary && r.nextInt(2) == 0);

            if (shouldCloseOut) {
              var member = run(r.nextInt(run.length)).asInstanceOf[GridCell];

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
      });
    }

    grid
  }
}
