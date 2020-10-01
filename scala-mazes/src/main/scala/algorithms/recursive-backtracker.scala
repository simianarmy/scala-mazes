package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class RecursiveBacktracker extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    def checkNeighbor(current: MazeCell, r: Int): Unit = {
      // get unvisited neighbors
      var neighbors = current.neighbors.filter(n => n.links.isEmpty)

      while (!neighbors.isEmpty) {
        val neighbor = RandomUtil.sample(neighbors)

        current.linkBidirectional(neighbor)
        checkNeighbor(neighbor, r + 1) // recurse
        neighbors = current.neighbors.filter(n => n.links.isEmpty)
      }
    }

    val start = startCell.getOrElse(grid.randomCell())
    checkNeighbor(start, 0) // recursive

    grid
  }

  override def toString: String = "Recursive Backtracker"
}

