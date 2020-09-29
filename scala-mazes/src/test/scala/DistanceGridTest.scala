import org.scalatest.FunSuite

import lib.DistanceGrid
import lib.MazeCell._
import algorithms.Wilsons

class DistanceGridTest extends FunSuite {
  test("distances assignment") {
    var grid = new DistanceGrid(2, 3)

    assert(grid.distances == null)

    grid.distances = grid.getCell(0, 0) match {
      case Some(start) => start.distances
      case _ => null
    }

    assert(grid.distances != null)
  }

  test("contentsOf") {
    var grid = new DistanceGrid(3, 3)
    val start = cellOrNil(grid.getCell(0, 0))
    val cellb = cellOrNil(grid.getCell(0, 1))
    val cellc = cellOrNil(grid.getCell(0, 2))

    start.link(cellb)
    cellb.link(cellc)

    grid.distances = start.distances
    assert(grid.contentsOf(cellOrNil(grid.getCell(0, 1))) == "1")
    assert(grid.contentsOf(cellOrNil(grid.getCell(0, 2))) == "2")
  }
}
