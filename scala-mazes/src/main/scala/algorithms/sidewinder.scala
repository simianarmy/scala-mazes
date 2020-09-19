package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, OrthogonalGrid, GridCell, MazeCell}

object Sidewinder {
  def on[T <: Grid](grid: T): T = {
    val r = scala.util.Random
    var run = new ArrayBuffer[MazeCell]()

    grid.eachRow((it: Iterator[MazeCell]) => {
      run.clear();

      while (it.hasNext) {
        val cell = it.next()
        run += cell;

        grid match {
          case OrthogonalGrid => {
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
      }
    });

    grid
  }
}
