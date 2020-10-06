import algorithms.GrowingTree
import lib.{RandomUtil}

object GrowingTreeApp extends MazeApp {
  val rand = new scala.util.Random(System.currentTimeMillis)

  // Simplified Prims
  val gg = new GrowingTree().on(makeGrid, None) { list => RandomUtil.sample(list) }
  gg.distances = gg.getCell(gg.rows / 2, gg.columns / 2).distances

  MazeApp.gridToPng(gg, "generated/growing-tree-random.png")

  // Recursive Backtracker
  val gg2 = new GrowingTree().on(makeGrid, None) { list => list.last }
  gg2.distances = gg2.getCell(gg.rows / 2, gg.columns / 2).distances

  MazeApp.gridToPng(gg2, "generated/growing-tree-last.png")

  // Mix of both
  val gg3 = new GrowingTree().on(makeGrid, None) { list =>
    if (rand.nextInt(2) == 0) list.last else RandomUtil.sample(list)
  }
  gg3.distances = gg3.getCell(gg.rows / 2, gg.columns / 2).distances

  MazeApp.gridToPng(gg3, "generated/growing-tree-mix.png")
}
