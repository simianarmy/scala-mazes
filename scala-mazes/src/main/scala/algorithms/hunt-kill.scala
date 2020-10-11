package algorithms

import lib.Grid
import lib.MazeCell
import lib.RandomUtil

class HuntKill extends MazeGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    var current = grid.randomCell()
    var looping = true

    while (looping) {
      val unvisitedNeighbors = current.neighbors.filter(n => n.links.isEmpty)

      if (unvisitedNeighbors.length > 0) {
        val neighbor = RandomUtil.sample(unvisitedNeighbors)
        current.linkBidirectional(neighbor)
        current = neighbor.asInstanceOf[A]
      } else {
        looping = false

        grid.eachCell(cell => {
          if (!looping) {
            val visitedNeighbors = cell.neighbors.filter(n => !n.links.isEmpty)

            if (cell.links.isEmpty && !visitedNeighbors.isEmpty) {
              current = cell
              val neighbor = RandomUtil.sample(visitedNeighbors)
              current.linkBidirectional(neighbor)
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
