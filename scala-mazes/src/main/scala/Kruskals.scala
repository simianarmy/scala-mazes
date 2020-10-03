import lib.{Kruskals, OrthogonalGrid, GridCell}

object KruskalsApp extends MazeApp {
  val g = new OrthogonalGrid[GridCell](rows, cols);
  val gg = new Kruskals().on(g)

  printMaze(gg)
}
