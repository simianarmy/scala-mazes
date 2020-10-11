/**
 * Generates circle (polar) grids
  */

object Polar extends MazeApp {
  val g = makePolarGrid

  val gg = generateMaze(g)

  printMaze(gg)
}
