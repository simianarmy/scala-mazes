package algorithms

import scala.collection.mutable.Stack
import lib.Grid
import lib.Cell
import lib.RandomUtil

object RecursiveBacktracker {
  def on[T <: Grid](grid: T, startAt: Cell = null): T = {
    def checkNeighbor(current: Cell): Unit = {
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

