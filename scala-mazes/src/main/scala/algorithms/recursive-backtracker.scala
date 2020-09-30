package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class RecursiveBacktracker extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    def checkNeighbor(current: MazeCell): Unit = {
      // get unvisited neighbors
      var neighbors = current.neighbors.filter(n => n.getLinks().isEmpty)

      while (!neighbors.isEmpty) {
        val neighbor = RandomUtil.sample(neighbors)

        current.linkBidirectional(neighbor)
        checkNeighbor(neighbor) // recurse
        neighbors = current.neighbors.filter(n => n.getLinks().isEmpty)
      }
    }

    val start = startCell.getOrElse(grid.randomCell())
    checkNeighbor(start) // recursive

    grid
  }

  override def toString: String = "Recursive Backtracker"
}

