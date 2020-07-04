import org.scalatest.FunSuite

class SidewinderTest extends FunSuite {
  test("Sidewinder generator") {
    var grid = new Grid(2, 3);
    var btg = Sidewinder.on(grid)

    assert(btg.size == grid.size)
  }
}
