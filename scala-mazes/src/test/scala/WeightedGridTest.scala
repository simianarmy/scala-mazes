import org.scalatest.FunSuite

import lib.{WeightedGrid, WeightedCell}
import lib.MazeCell._
import algorithms.AldousBroder

class WeightedGridTest extends FunSuite {
  test("constructor") {
    val wg = new WeightedGrid(4, 5)
    assert(wg.id === "we")
    assert(wg.size === 20)
  }

  test("distances=") {
    val wg = new WeightedGrid(4, 5)
    val gg = new AldousBroder().on(wg, None)()

    val start = gg.getCell(0, 0)
    val finish = gg.getCell(gg.rows - 1, gg.columns - 1)

    gg.distances = start.distances.pathTo(finish)
    assert(gg.distances.get(start) == 0)
    assert(gg.distances.get(finish) > 0)
  }
}
