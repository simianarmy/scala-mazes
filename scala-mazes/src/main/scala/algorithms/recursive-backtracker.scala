package algorithms

import lib.Grid
import lib.RandomUtil
import lib.MazeCell

class RecursiveBacktracker extends GeneralGenerator {
  override def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    new GrowingTree().on(grid, startCell) { list => list.last }
  }

  override def toString: String = "Recursive Backtracker"
}

