import org.scalatest.FunSuite


class GridTest extends FunSuite {
  test("Grid constructor") {
    var grid = new Grid(2, 3);

    assert(grid.rows === 2);
    assert(grid.columns === 3);
  }

  test("Grid row iterator") {
    var grid = new Grid(2, 3);

    grid.eachRow(row => {
      assert(row.length === 3);
      assert(row(0).column === 0);
    })
  }

  test("Grid cell iterator") {
    var grid = new Grid(2, 3);

    grid.eachCell(cell => {
      assert(grid.getCell(cell.row, cell.column) == cell);
    })
  }
}
