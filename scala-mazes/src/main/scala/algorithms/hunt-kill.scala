package algorithms

import lib.Grid
import lib.MazeCell
import lib.RandomUtil

class HuntKill extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    var current = grid.randomCell()
    var looping = true

    while (looping) {
      val unvisitedNeighbors = current.neighbors.filter(n => n.getLinks().isEmpty)

      if (unvisitedNeighbors.length > 0) {
        val neighbor = RandomUtil.sample(unvisitedNeighbors)
        current.link(neighbor)
        current = neighbor.asInstanceOf[A]
      } else {
        looping = false

        grid.eachCell(cell => {
          if (!looping) {
            val visitedNeighbors = cell.neighbors.filter(n => !n.getLinks().isEmpty)

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

  override def toString: String = "Hunt & Kill"
}
