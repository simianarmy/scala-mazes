import lib.HexGrid

object HexGridApp extends MazeApp {
  val g = new HexGrid(rows, cols);
  val gg = generateMaze(g)

  printMaze(gg)
}
