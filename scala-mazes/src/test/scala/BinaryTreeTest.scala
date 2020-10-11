import org.scalatest.FunSuite

import lib.DistanceGrid
import algorithms.BinaryTree

class BinaryTreeTest extends FunSuite {
  test("BinaryTree generator") {
    var grid = new DistanceGrid(2, 3);
    var btg = new BinaryTree().on(grid, None)()

    assert(btg.size == grid.size)
  }
}
