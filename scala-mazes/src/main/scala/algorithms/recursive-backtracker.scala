package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class RecursiveBacktracker extends GeneralGenerator {
  def on[A <: MazeCell,B](grid: Grid[A,B], startAt: A = null): Grid[A,B] = {
    def checkNeighbor(current: MazeCell): Unit = {
      // get unvisited neighbors
      var neighbors = current.neighbors.filter(n => n.getLinks().isEmpty)

      while (!neighbors.isEmpty) {
        val neighbor = RandomUtil.sample(neighbors)
        current.link(neighbor)
        checkNeighbor(neighbor)
        neighbors = current.neighbors.filter(n => n.getLinks().isEmpty)
      }
    }

    val start = if (startAt == null) grid.randomCell() else startAt
    checkNeighbor(start) // recursive

    grid
  }
}

