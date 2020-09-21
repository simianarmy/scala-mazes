import org.scalatest.FunSuite

import lib.OrthogonalGrid

class OrthogonalGridTest extends FunSuite {
  test("Grid constructor") {
    var grid = new OrthogonalGrid(2, 3);

    assert(grid.rows === 2);
    assert(grid.columns === 3);
  }

  test("[]") {
    var grid = new OrthogonalGrid(3, 3);
    val cell11 = grid.getCell(1, 1)
    assert(cell11 == grid(1)(1))
  }

  test("Grid row iterator") {
    var grid = new OrthogonalGrid(2, 3);

    grid.eachRow(it => {
      val c0 = it.next()
      assert(c0.column === 0);
    })
  }

  test("Grid cell iterator") {
    var grid = new OrthogonalGrid(2, 3);

    grid.eachCell(cell => {
      assert(grid(cell.row)(cell.column) == cell);
    })
  }
}
