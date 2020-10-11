import scala.reflect.{ClassTag, classTag}

import lib._

class SimpleOverCell(row: Int, column: Int, grid: WeaveGrid) extends OverCell(row, column, grid) {
  // just the original neighbors method
  override def neighbors: List[MazeCell] = {
    List(north, south, east, west).filterNot(p => p.isNil)
  }
}

class PreconfiguredGrid(rows: Int, columns: Int) extends WeaveGrid(rows, columns) {
  override def createCell[A <: GridCell : ClassTag](i: Int, j: Int): A = new SimpleOverCell(i, j, this).asInstanceOf[A]
  override def nilCell[A <: GridCell : ClassTag]: A = createCell(-1, -1)
}

object KruskalsWeave extends MazeApp {
  val g = if (conf.rainbow) new PreconfiguredGrid(rows, cols) with RainbowColored[OverCell];
  else new PreconfiguredGrid(rows, cols) with Colored[OverCell];

  val kruskals = new Kruskals()
  val state = kruskals.newState(g)
  val rand = new scala.util.Random(System.currentTimeMillis)

  for (i <- 0 until g.size) {
    val row = 1 + rand.nextInt(g.rows - 2)
    val col = 1 + rand.nextInt(g.columns - 2)
    state.addCrossing(g.getCell(row, col))
  }

  val gg = kruskals.on(g, state)

  MazeApp.gridToPng(gg, "generated/maze-kruskals-weave.png")
}
