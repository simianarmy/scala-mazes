import org.scalatest.FunSuite

import lib.Mask
import lib.MaskedGrid

class MaskedGridTest extends FunSuite {
  test("MaskedGrid constructor") {
    var mask = new Mask(4, 4)
    val grid = new MaskedGrid(mask);

    assert(grid.rows === 4);
    assert(grid.columns === 4);
  }

  test("numCells") {
    var mask = new Mask(4, 4)
    val grid = new MaskedGrid(mask);

    assert(grid.size() == 16)
    mask(2)(2) = false
    assert(grid.size() == 15)
  }
}

