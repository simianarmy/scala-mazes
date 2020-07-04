import org.scalatest.FunSuite

class DistancesTest extends FunSuite {
  test("Distances constructor") {
    var c = new Cell(1, 1)
    var d = new Distances(c)

    assert(d.get(c) === 0)
  }

  test("Distances.set/get") {
    var c = new Cell(1, 1)
    var c2 = new Cell(1, 2)
    var d = new Distances(c)

    d.set(c, 10)
    assert(d.get(c) === 10)
    assert(d.get(c2) === Distances.NotFound)
  }

  test("Distances.cells") {
    var c = new Cell(1, 1)
    var c2 = new Cell(1, 2)
    var d = new Distances(c)

    d.set(c2, 2)
    assert(d.cells.size === 2)
  }
}
