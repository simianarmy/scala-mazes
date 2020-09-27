package algorithms

import lib._

class BinaryTree extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    def run() = {
      grid.eachCell(cell => {
        val gc = cell.asInstanceOf[GridCell]
        val east = grid.getCell(cell.row, cell.column + 1).asInstanceOf[GridCell]
        val neighbors = List[GridCell](gc.north, east).filter(_ != null)

        if (!neighbors.isEmpty) {
          cell.linkBidirectional(RandomUtil.sample(neighbors))
        }
      })
      grid
    }

    grid match {
      case OrthogonalGrid(_,_) => run()
      case HexGrid(_,_) => run()
      case TriangleGrid(_,_) => run()
      case _ => grid
    }
  }

  override def toString: String = "Binary Tree"
}
