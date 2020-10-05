import lib.{Kruskals, WeaveGrid, Colored, OverCell}

object KruskalsApp extends MazeApp {
  val g = new WeaveGrid(rows, cols)
  val k = new Kruskals()
  val gg = k.on(g, k.newState(g))

  MazeApp.gridToPng(gg, "generated/maze-kruskals.png")
}
