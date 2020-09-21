import lib.PolarGrid

object PolarGridApp extends MazeApp {
  val g = new PolarGrid(rows);
  val gg = generateMaze(g)

  printMaze(gg)
}
