import lib.WeaveGrid

object WeaveGridApp extends MazeApp {
  val g = new WeaveGrid(rows, cols);
  val gg = generateMaze(g)

  printMaze(gg)
}
