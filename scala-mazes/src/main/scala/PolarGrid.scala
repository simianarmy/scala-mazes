import lib.PolarGrid

object PolarGridApp extends MazeApp {
  val g = new PolarGrid(rows);

  printMaze(generateMaze(g))
}
