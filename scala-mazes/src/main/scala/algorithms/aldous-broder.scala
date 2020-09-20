package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class AldousBroder extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    val r = scala.util.Random;
    var cell: A = grid.randomCell()
    var unvisited = grid.numCells - 1

    while (unvisited > 0) {
      val neighbor = RandomUtil.sample(cell.neighbors)

      if (neighbor.getLinks().isEmpty) {
        cell.link(neighbor)
        unvisited -= 1
      }

      cell = neighbor.asInstanceOf[A]
    }

    grid
  }
}
