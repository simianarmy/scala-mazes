package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class AldousBroder extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    var cell: A = grid.randomCell()
    var unvisited = grid.size - 1

    while (unvisited > 0) {
      val neighbor = RandomUtil.sample(cell.neighbors)

      if (neighbor.links.isEmpty) {
        cell.linkBidirectional(neighbor)
        unvisited -= 1
      }

      cell = neighbor.asInstanceOf[A]
    }

    grid
  }

  override def toString: String = "Aldous-Broder"
}
