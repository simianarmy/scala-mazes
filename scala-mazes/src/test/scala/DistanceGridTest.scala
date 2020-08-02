import org.scalatest.FunSuite

import lib.DistanceGrid

class DistanceGridTest extends FunSuite {
  test("DistanceGrid constructor") {
    var grid = new DistanceGrid(2, 3);

    assert(grid.rows === 2);
    assert(grid.columns === 3);
  }

  test("DistanceGrid inheritance") {
    var grid = new DistanceGrid(2, 3);
    var cell = grid.getCell(1, 1)
    assert(cell.row === 1)
    assert(cell.column === 1)
  }
}
