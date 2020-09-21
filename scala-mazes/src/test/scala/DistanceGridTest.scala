import org.scalatest.FunSuite

import lib.DistanceGrid
import algorithms.Wilsons

class DistanceGridTest extends FunSuite {
  test("distances assignment") {
    var grid = new DistanceGrid(2, 3)

    assert(grid.distances == null)

    val start = grid.getCell(0, 0)
    grid.distances = start.distances

    assert(grid.distances != null)
  }

  test("contentsOf") {
    var grid = new DistanceGrid(3, 3)
    val start = grid.getCell(0, 0)
    val cellb = grid.getCell(0, 1)
    val cellc = grid.getCell(0, 2)

    start.link(cellb)
    cellb.link(cellc)

    grid.distances = start.distances
    assert(grid.contentsOf(grid.getCell(0, 1)) == "1")
    assert(grid.contentsOf(grid.getCell(0, 2)) == "2")
  }
}
