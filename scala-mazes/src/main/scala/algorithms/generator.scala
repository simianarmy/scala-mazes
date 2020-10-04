package algorithms

import lib.{Grid, MazeCell, GridCell, PolarCell}

trait GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = grid
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = grid
}

