package algorithms

import lib.Grid
import lib.RandomUtil

object HuntKill {
  def on[T <: Grid](grid: T): T = {
    var current = grid.randomCell()
    var looping = true

    while (looping) {
      val unvisitedNeighbors = current.neighbors().filter(n => n.getLinks().isEmpty)

      if (unvisitedNeighbors.length > 0) {
        val neighbor = RandomUtil.sample(unvisitedNeighbors)
        current.link(neighbor)
        current = neighbor
      } else {
        looping = false

        grid.eachCell(cell => {
          if (!looping) {
            val visitedNeighbors = cell.neighbors().filter(n => !n.getLinks().isEmpty)

            if (cell.getLinks().isEmpty && !visitedNeighbors.isEmpty) {
              current = cell
              val neighbor = RandomUtil.sample(visitedNeighbors)
              current.link(neighbor)
              looping = true // break out of this iterator
            }
          }
        })
      }
    }
    grid
  }
}
