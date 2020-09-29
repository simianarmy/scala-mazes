import org.scalatest.FunSuite

import lib.PolarGrid
import lib.MazeCell._

class PolarGridTest extends FunSuite {
  test("Grid constructor") {
    var grid = new PolarGrid(3);

    assert(grid.rows === 3);
    assert(grid.columns === 1);
    assert(grid.getCell(1, 0) != null)
  }

  test("cellAt") {
    var grid = new PolarGrid(3);
    val cell10 = grid.cellAt(1)
    assert(cell10.row == 1)
    assert(cell10.column == 0)
  }

  test("getCell") {
    var grid = new PolarGrid(3);
    val cell11 = cellOrNil(grid.getCell(1, 1))
    assert(cell11 == grid(1)(1))
    assert(cellOrNil(grid.getCell(10, 10)).isNil)
  }

  test("row iterator") {
    var grid = new PolarGrid(3);
    var lastRow = 0

    grid.eachRow(it => {
      val c0 = it.next()
      assert(c0.row == lastRow, c0)
      lastRow += 1
    })
  }

  test("cell iterator") {
    var grid = new PolarGrid(3);

    grid.eachCell(cell => {
      assert(cellOrNil(grid.getCell(cell.row, cell.column)) == cell);
    })
  }
}
