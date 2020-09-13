package algorithms

import lib.Grid
import lib.Cells
import lib.Cell

object BinaryTree {
  def on[T <: Grid](grid: T): T = {
    val r = scala.util.Random;
    var neighbors = new Cells()

    grid.eachCell((cell: Cell) => {
      neighbors.clear();

      if (cell.north != null) {
        neighbors += cell.north;
      }

      if (cell.east != null) {
        neighbors += cell.east;
      }

      if (!neighbors.isEmpty) {
        val index = r.nextInt(neighbors.length);
        val neighbor = neighbors(index);

        if (neighbor != null) {
          cell.link(neighbor);
        }
      }
    });

    grid
  }
}
