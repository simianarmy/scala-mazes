package algorithms

import lib.{Grid, MazeCell, GridCell, PolarCell}

trait Generator
trait MazeGenerator extends Generator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: (List[A] => A) = null): Grid[A]
}

trait GridGenerator extends Generator {
  def on[A <: GridCell](grid: Grid[A], startCell: Option[A])(op: (List[A] => A) = null): Grid[A]
}

