package algorithms

import lib.Grid

object AldousBroder {
  def on[T <: Grid](grid: T): T = {
    val r = scala.util.Random;
    var cell = grid.randomCell()
    var unvisited = grid.numCells - 1

    while (unvisited > 0) {
      val neighbor = cell.neighbors()(r.nextInt(cell.neighbors().length));

      if (neighbor.getLinks().isEmpty) {
        cell.link(neighbor)
        unvisited -= 1
      }

      cell = neighbor.asInstanceOf[grid.CellType]
    }

    grid
  }
}
