package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

object RecursiveBacktracker {
  def on[T <: Grid](grid: T, startAt: MazeCell = null): T = {
    def checkNeighbor(current: MazeCell): Unit = {
      // get unvisited neighbors
      var neighbors = current.neighbors().filter(n => n.getLinks().isEmpty)

      while (!neighbors.isEmpty) {
        val neighbor = RandomUtil.sample(neighbors)
        current.link(neighbor)
        checkNeighbor(neighbor)
        neighbors = current.neighbors().filter(n => n.getLinks().isEmpty)
      }
    }

    val start = if (startAt == null) grid.randomCell() else startAt
    checkNeighbor(start) // recursive

    grid
  }
}

