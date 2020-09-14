package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{OrthogonalGrid, GridCell, Cell}

object Sidewinder {
  def on[T <: OrthogonalGrid](grid: T): T = {
    val r = scala.util.Random
    var run = new ArrayBuffer[grid.CellType]()

    grid.eachRow(it => {
      run.clear();

      while (it.hasNext) {
        val cell = it.next()
        run += cell;

        val atEasternBoundary = (cell.east == null);
        val atNorthernBoundary = (cell.north == null);
        val shouldCloseOut =
          atEasternBoundary || (!atNorthernBoundary && r.nextInt(2) == 0);

        if (shouldCloseOut) {
          var member = run(r.nextInt(run.length));

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
