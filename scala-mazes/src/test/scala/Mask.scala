import org.scalatest.FunSuite
import scala.util.{Success,Failure}

import lib.Mask

class MaskTest extends FunSuite {
  test("Mask constructor") {
    var mask = new Mask(4, 4)

    assert(mask.rows == 4)
    assert(mask.columns == 4)

    for (i <- 0 until mask.rows; j <- 0 until mask.columns) {
      assert(mask.bits(i)(j))
    }
  }

  test("accessor (in-bounds)") {
    var mask = new Mask(4, 4)

    assert(mask(2)(2))
  }

  test("accessor (out of bounds)") {
    var mask = new Mask(4, 4)

    assertThrows[IndexOutOfBoundsException] {
      mask(8)(8)
    }
  }

  test("setter") {
    var mask = new Mask(4, 4)

    assert(mask(2)(2))
    mask(2)(2) = false
    assert(!mask(2)(2))
  }

  test("count") {
    var mask = new Mask(2, 2)

    assert(mask.count() == 4)
    mask(1)(1) = false
    assert(mask.count() == 3)
  }

  test("random location") {
    var mask = new Mask(2, 2)
    val randLoc = mask.randomLocation()

    assert(randLoc._1 >= 0)
    assert(randLoc._2 >= 0)
  }

  test("fromTxt") {
    val mask = Mask.fromTxt("src/main/scala/masks/simple.txt") match {
      case Success(i) => i
      case Failure(s) => null
    }
    assert(mask.count() > 10)
    assert(!mask(0)(0))
    assert(mask(0)(1))
  }
}
