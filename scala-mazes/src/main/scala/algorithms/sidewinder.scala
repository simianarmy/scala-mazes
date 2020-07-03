//package algorithms

import scala.collection.mutable.ArrayBuffer
//import Grid
//import Cell

object Sidewinder {
  def on(grid: Grid): Grid = {
    val r = scala.util.Random;
    var run = ArrayBuffer[Cell]();

    grid.eachRow((row: Array[Cell]) => {
      run.clear();

      for (i <- 0 until row.length) {
        val cell: Cell = row(i);

        run += cell;
        val atEasternBoundary: Boolean = (cell.east == null);
        val atNorthernBoundary: Boolean = (cell.north == null);
        val shouldCloseOut: Boolean =
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

    return grid;
  }
}
