package algorithms

import lib.{Grid, MazeCell, OrthogonalGrid, RandomUtil}
import lib.MazeCell._

class BinaryTree extends MazeGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    grid.eachCell(cell => {
      val east = grid.getCell(cell.row, cell.column + 1)
      val neighbors = List[MazeCell](cell.north, east).filterNot(p => p.isNil)

      if (!neighbors.isEmpty) {
        cell.linkBidirectional(RandomUtil.sample(neighbors))
      }
    })
    grid
  }

  override def toString: String = "Binary Tree"
}
