import lib.TriangleGrid

object DeltaGridApp extends MazeApp {
  val g = new TriangleGrid(rows, cols);
  val gg = generateMaze(g)

  printMaze(gg)
}
