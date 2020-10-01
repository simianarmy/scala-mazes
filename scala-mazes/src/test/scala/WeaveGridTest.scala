import org.scalatest.FunSuite

import lib.{WeaveGrid, OverCell, UnderCell}
import lib.MazeCell._
import algorithms.AldousBroder

class WeaveGridTest extends FunSuite {
  test("constructor") {
    val wg = new WeaveGrid(10, 10)
    assert(wg.underCells.isEmpty)
    assert(wg.getCell(0, 0).isInstanceOf[OverCell])
  }

  test("tunnelUnder") {
    val wg = new WeaveGrid(10, 10)
    wg.tunnelUnder(wg.getCell(2, 2))
    assert(!wg.underCells.isEmpty)
  }
}
