package algorithms

import lib.Grid
import lib.Cells
import lib.Cell

object Sidewinder {
  def on[T <: Grid](grid: T): T = {
    val r = scala.util.Random
    var run = new Cells()

    grid.eachRow((it: Iterator[Cell]) => {
      run.clear();

      while (it.hasNext) {
        val cell: Cell = it.next()
        run += cell;

        val atEasternBoundary = (cell.east == null);
        val atNorthernBoundary = (cell.north == null);
        val shouldCloseOut =
          atEasternBoundary || (!atNorthernBoundary && r.nextInt(2) == 0);

        if (shouldCloseOut) {
          var member: Cell = run(r.nextInt(run.length));

          if (member.north != null) {
            member.link(member.north);
          }
          run.clear();
        } else {
          cell.link(cell.east);
        }
      }
    });

    grid
  }
}
