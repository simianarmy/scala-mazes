package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class RecursiveBacktracker extends MazeGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    new GrowingTree().on(grid, startCell) { list => list.last }
  }

  override def toString: String = "Recursive Backtracker"
}

