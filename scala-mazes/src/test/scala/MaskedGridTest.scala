import org.scalatest.FunSuite
import scala.util.{Success,Failure}

import lib.Mask
import lib.MaskedGrid
import lib.MazeCell._

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

    assert(grid.numCells == 16)
    mask(2)(2) = false

    val mg2 = new MaskedGrid(mask)
    assert(mg2.numCells == 15)
    assert(mg2.numCells < mg2.size())
  }

  test("off Cells") {
    var mask = new Mask(4, 4)
    mask(2)(2) = false
    val grid = new MaskedGrid(mask);

    val cell22 = grid.getCell(2, 2) match {
      case Some(cell) => cell
      case _ => None
    }

    assert(cellOrNil(grid.getCell(2, 2)).isNil)
  }

  test("from text mask") {
    val mask = Mask.fromTxt("masks/simple.txt") match {
      case Success(i) => i
      case Failure(s) => null
    }
    val g = new MaskedGrid(mask)

    assert(g.rows == 10)
    assert(g.columns == 10)
    assert(g.numCells == mask.count())
  }
}

