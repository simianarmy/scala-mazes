package algorithms

import scala.collection.mutable.ArrayBuffer
import lib.{Grid, MazeCell}
import lib.RandomUtil

class Wilsons extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    var unvisited = ArrayBuffer[MazeCell]()

    grid.eachCell((c: A) => {
      unvisited.append(c)
    })

    val first = RandomUtil.sample(unvisited)
    unvisited -= first

    while (!unvisited.isEmpty) {
      var cell = RandomUtil.sample(unvisited)
      var path = new ArrayBuffer[MazeCell]()
      path += cell

      while (unvisited.contains(cell)) {
        cell = RandomUtil.sample(cell.neighbors)
        val position = path.indexOf(cell)

        if (position > 0) {
          path.sliceInPlace(0, position+1)
        } else {
          path += cell
        }
      }

      for (i <- 0 to path.length - 2) {
        path(i).linkBidirectional(path(i + 1))
        unvisited -= path(i)
      }
    }

    grid
  }
  override def toString: String = "Wilsons"
}

