package algorithms

import lib.{Grid, MazeCell, GridCell, PolarCell}

trait GeneralGenerator {
  def on[A <: MazeCell,B](grid: Grid[A,B]): Grid[A,B]
  def on[A <: MazeCell,B](grid: Grid[A,B], startCell: A): Grid[A,B]
}

trait GridGenerator {
  def on[A <: GridCell,B](grid: Grid[A,B]): Grid[A,B]
  def on[A <: GridCell,B](grid: Grid[A,B], startCell: A): Grid[A,B]
}

trait PolarGenerator {
  def on[A <: PolarGenerator,B](grid: Grid[A,B]): Grid[A,B]
  def on[A <: PolarGenerator,B](grid: Grid[A,B], startCell: A): Grid[A,B]
}


