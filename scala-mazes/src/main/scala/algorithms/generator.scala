package algorithms

import lib.{Grid, MazeCell, GridCell, PolarCell}

trait Randomizer {
  val r = scala.util.Random;
}

trait GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A]
}

trait GridGenerator {
  def on[A <: GridCell](grid: Grid[A], startCell: Option[A]): Grid[A]
}

trait PolarGenerator {
  def on[A <: PolarCell](grid: Grid[A], startCell: Option[A]): Grid[A]
}


