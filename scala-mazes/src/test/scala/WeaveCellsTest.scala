import org.scalatest.FunSuite

import lib.{OverCell, UnderCell, WeaveGrid}
import lib.MazeCell._

class WeaveCellsTest extends FunSuite {
  test("OverCell constructor") {
    val oc = new OverCell(2, 2, new WeaveGrid(5, 5))
    assert(oc.row == 2)
  }

  test("linking") {
    val wg = new WeaveGrid(5, 5)
    val oc = new OverCell(2, 2, wg)
    oc.link(wg.getCell(2, 3))
    assert(oc.isLinked(wg.getCell(2, 3)))
  }

  test("canTunnelEast") {
    val wg = new WeaveGrid(5, 5)
    val oc = new OverCell(2, 2, wg)
    val east = wg.getCell(2, 3)

    oc.east = east
    east.east = wg.getCell(east.row, east.column + 1)
    // east links n & s.
    east.link(east.north)
    east.link(east.south)

    assert(oc.canTunnelEast)
  }

  test("isHorizontalPassage") {
    val wg = new WeaveGrid(5, 5)
    val hoc = new OverCell(2, 2, wg)

    hoc.west = wg.getCell(2, 1)
    hoc.east = wg.getCell(2, 3)
    hoc.link(wg.getCell(2, 1))
    hoc.link(wg.getCell(2, 3))

    assert(hoc.isHorizontalPassage)
  }

  test("UnderCell constructor (vertical)") {
    val wg = new WeaveGrid(5, 5)
    val voc = new OverCell(2, 2, wg)

    voc.east = wg.getCell(2, 3)
    voc.west = wg.getCell(2, 1)
    val uc = new UnderCell(voc)

    assert(uc.isLinked(uc.east))
    assert(uc.isLinked(uc.west))
  }

  test("UnderCell constructor (horizontal)") {
    val wg = new WeaveGrid(5, 5)
    val hoc = new OverCell(2, 2, wg)

    // make overcell a horizontal passage
    hoc.west = wg.getCell(2, 1)
    hoc.east = wg.getCell(2, 3)
    hoc.link(wg.getCell(2, 1))
    hoc.link(wg.getCell(2, 3))

    hoc.north = wg.getCell(1, 2)
    hoc.south = wg.getCell(3, 2)

    val huc = new UnderCell(hoc)

    assert(huc.isLinked(huc.north))
    assert(huc.isLinked(huc.south))
  }
}


