package algorithms

import scala.collection.mutable.ArrayBuffer
import lib.Grid
import lib.Cell

object BinaryTree {
  def on[T <: Grid](grid: T): T = {
    println("BinaryTree")
    val r = scala.util.Random;
    var neighbors = ArrayBuffer[Cell]();

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
