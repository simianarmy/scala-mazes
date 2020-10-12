import org.scalatest.FunSuite

import lib._

class Grid3DTest extends FunSuite {
  test("constructor") {
    val wg = new Grid3D(4, 3, 3)
    assert(wg.size === 36)
  }

  test("getCell") {
    val wg = new Grid3D(4, 3, 3)
    val cell = wg.getCell(1, 2, 1)
    assert(!cell.isNil)
    assert(cell.level == 1)
    assert(cell.row == 2)
    assert(cell.column == 1)
  }

  test("getAt") {
    val wg = new Grid3D(4, 3, 3)
    var cell = wg.cellAt(23)
    assert(!cell.isNil)
    assert(cell.level == 2)
    assert(cell.row == 1)
    assert(cell.column == 2)

    cell = wg.cellAt(5)
    assert(cell.level == 0)
    assert(cell.row == 1)
    assert(cell.column == 2)
  }

  test("eachLevel") {
    val wg = new Grid3D(4, 3, 5)
    wg.eachLevel(l => {
      assert(l.size == 3)
    })
  }

  test("eachRow") {
    val wg = new Grid3D(4, 3, 5)
    wg.eachRow(row => {
      assert(row.size == 5)
    })
  }
}

