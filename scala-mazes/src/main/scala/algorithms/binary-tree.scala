package algorithms

import lib.{Grid, OrthogonalGrid}
import lib.RandomUtil
import lib.{MazeCell, GridCell}

object BinaryTree {
  def on[A <: GridCell,B](grid: Grid[A,B]): Grid[A,B] = {
    def run() = {
      grid.eachCell(cell => {
        val neighbors = List[GridCell](cell.north, cell.east).filter(_ != null)

        if (!neighbors.isEmpty) {
          cell.link(RandomUtil.sample(neighbors))
        }
      })
      grid
    }

    grid match {
      case OrthogonalGrid(_,_) => run()
      case _ => grid
    }
  }
}
