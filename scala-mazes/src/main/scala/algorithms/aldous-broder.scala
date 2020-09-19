package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

object AldousBroder {
  def on[T <: Grid](grid: T): T = {
    val r = scala.util.Random;
    var cell = grid.randomCell()
    var unvisited = grid.numCells - 1

    while (unvisited > 0) {
      val neighbor: MazeCell = RandomUtil.sample(cell.neighbors())

      if (neighbor.getLinks().isEmpty) {
        cell.link(neighbor)
        unvisited -= 1
      }

      cell = neighbor.asInstanceOf[MazeCell]
    }

    grid
  }
}
