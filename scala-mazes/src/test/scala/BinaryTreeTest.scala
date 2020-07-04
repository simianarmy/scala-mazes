import org.scalatest.FunSuite

class BinaryTreeTest extends FunSuite {
  test("BinaryTree generator") {
    var grid = new DistanceGrid(2, 3);
    var btg = BinaryTree.on(grid)

    assert(btg.size == grid.size)
  }
}
