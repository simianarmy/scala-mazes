import org.scalatest.FunSuite

import lib.{SphereGrid, HemisphereCell}
import lib.MazeCell._
import algorithms.Sidewinder

class SphereGridTest extends FunSuite {
  test("constructor") {
    val wg = new SphereGrid(10)
    assert(wg.equator === 5)
  }

  test("getCell") {
    val wg = new SphereGrid(10)
    assert(wg.getCell(0, 1, 0).isInstanceOf[HemisphereCell])
    assert(wg.getCell(0, 1, 0).row == 1)
    assert(wg.getCell(0, 1, 2).column == 2)
  }

  test("size") {
    val sg = new SphereGrid(10)
    assert(sg.size(0) == 1)
    assert(sg.size(1) >= sg.size(0))
    assert(sg.size(sg.equator-1) >= sg.size(sg.equator-2))
  }

  test("eachCell") {
    val sg = new SphereGrid(10)
    // just dont throw an exception is good enough
    var counter = 0
    sg.eachCell(cell => {
      //println(cell)
      counter = counter + 1
    })
    assert(counter > 0)
  }
}
