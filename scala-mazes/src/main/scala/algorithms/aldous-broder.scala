package algorithms

import scala.collection.mutable.ArrayBuffer
import lib.Grid
import lib.Cell

object AldousBroder {
  def on[T <: Grid](grid: T): T = {
    println("Aldous-Broder")
    val r = scala.util.Random;
    //var neighbors = ArrayBuffer[Cell]();
    var cell = grid.randomCell()
    var unvisited = grid.numCells - 1

    while (unvisited > 0) {
      val neighbor = cell.neighbors()(r.nextInt(cell.neighbors().length));

      if (neighbor.getLinks().isEmpty) {
        cell.link(neighbor)
        unvisited -= 1
      }

      cell = neighbor
    }

    grid
  }
}
