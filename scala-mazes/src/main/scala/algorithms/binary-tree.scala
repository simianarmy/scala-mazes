package algorithms

import lib.{MazeCell, Grid, GridCell, OrthogonalGrid, RandomUtil}
import lib.MazeCell._

class BinaryTree extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    def run() = {
      grid.eachCell(cell => {
        val gc = cell.asInstanceOf[GridCell]
        val east = grid.getCell(cell.row, cell.column + 1).asInstanceOf[GridCell]
        val neighbors = List[GridCell](gc.north, east).filterNot(p => p.isNil)

        if (!neighbors.isEmpty) {
          cell.linkBidirectional(RandomUtil.sample(neighbors))
        }
      })
      grid
    }

    grid match {
      case OrthogonalGrid(_,_) => run()
      case _ => grid
    }
  }

  override def toString: String = "Binary Tree"
}
