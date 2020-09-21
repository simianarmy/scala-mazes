import org.scalatest.FunSuite

import lib.{MazeCell, PolarCell}

class PolarCellTest extends FunSuite {
  test("PolarCell constructor") {
    val cell = new PolarCell(2, 4)
    assert(cell.row == 2)
    assert(cell.column == 4)
  }
}
