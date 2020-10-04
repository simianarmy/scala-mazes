import algorithms.GrowingTree
import lib.{ColoredGrid, OrthogonalGrid, RandomUtil, GridCell}

object GrowingTreeApp extends MazeApp {
  // Simplified Prims
  val rand = new scala.util.Random(System.currentTimeMillis)
  val g = new ColoredGrid(rows, cols)
  val gg = new GrowingTree().on(g, None) { list => RandomUtil.sample(list) }
  gg.distances = gg.getCell(g.rows / 2, g.columns / 2).distances

  MazeApp.gridToPng(gg, "generated/growing-tree-random.png")

  // Recursive Backtracker
  val g2 = new ColoredGrid(rows, cols)
  val gg2 = new GrowingTree().on(g2, None) { list => list.last }
  gg2.distances = gg2.getCell(g.rows / 2, g.columns / 2).distances

  MazeApp.gridToPng(gg2, "generated/growing-tree-last.png")

  // Mix of both
  val g3 = new ColoredGrid(rows, cols)
  val gg3 = new GrowingTree().on(g3, None) { list =>
    if (rand.nextInt(2) == 0) list.last else RandomUtil.sample(list)
  }
  gg3.distances = gg3.getCell(g.rows / 2, g.columns / 2).distances

  MazeApp.gridToPng(gg3, "generated/growing-tree-mix.png")
}
