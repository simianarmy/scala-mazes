import lib.{WeightedGrid, GridCell}
import lib.RandomUtil
import lib.MazeCell._

object WeightedGridApp extends MazeApp {
  val g = new WeightedGrid(rows, cols);
  val gg = generateMaze(g, "rb")

  gg.braid(0.5)
  val start = gg.getCell(0, 0)
  val finish = gg.getCell(gg.rows - 1, gg.columns - 1)

  gg.distances = start.distances.pathTo(finish)
  MazeApp.gridToPng(gg, "generated/weighted-original.png")

  val lava = RandomUtil.sample(gg.distances.cells.toArray)
  lava.weight = 50

  gg.distances = start.distances.pathTo(finish)
  MazeApp.gridToPng(gg, "generated/weighted-rerouted.png")
}
