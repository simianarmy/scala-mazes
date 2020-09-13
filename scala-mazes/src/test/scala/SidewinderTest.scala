import org.scalatest.FunSuite

import lib.OrthogonalGrid
import algorithms.Sidewinder

class SidewinderTest extends FunSuite {
  test("Sidewinder generator") {
    var grid = new OrthogonalGrid(2, 3);
    var btg = Sidewinder.on(grid)

    assert(btg.size == grid.size)
  }
}
