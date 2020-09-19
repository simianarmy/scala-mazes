package algorithms

import lib.{Grid, OrthogonalGrid}
import lib.RandomUtil
import lib.{MazeCell, GridCell}

object BinaryTree {
  def on[T <: Grid](grid: T): T = {
    def run() = {
      grid.eachCell((cell: MazeCell) => {
        // TODO: case cell to a GridCell??
        val gc = cell.asInstanceOf[GridCell]
        val neighbors = List[GridCell](gc.north, gc.east).filter(_ != null)

        if (!neighbors.isEmpty) {
          cell.link(RandomUtil.sample(neighbors))
        }
      })
      grid
    }

    grid match {
      case OrthogonalGrid => run()
      case _ => grid
    }
  }
}
