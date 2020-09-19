package algorithms

import lib.Grid
import lib.MazeCell
import lib.RandomUtil

object HuntKill {
  def on[A,B](grid: Grid[A,B]): Grid[A,B] = {
    var current = grid.randomCell()
    var looping = true

    while (looping) {
      val unvisitedNeighbors = current.neighbors[MazeCell].filter(n => n.getLinks().isEmpty)

      if (unvisitedNeighbors.length > 0) {
        val neighbor = RandomUtil.sample(unvisitedNeighbors)
        current.link(neighbor)
        current = neighbor
      } else {
        looping = false

        grid.eachCell(cell => {
          if (!looping) {
            val visitedNeighbors = cell.neighbors[MazeCell].filter(n => !n.getLinks().isEmpty)

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
