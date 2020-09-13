package algorithms

import lib.Grid
import lib.Cells
import lib.Cell

object Wilsons {
  def on[T <: Grid](grid: T): T = {
    var unvisited = new Cells()
    val r = new scala.util.Random(System.currentTimeMillis)

    def sampleCell(cells: Cells) =
      cells(r.nextInt(cells.length))

    grid.eachCell(unvisited.append)

    val first = sampleCell(unvisited)
    unvisited -= first

    while (!unvisited.isEmpty) {
      var cell = sampleCell(unvisited)
      var path: Cells = new Cells()
      path += cell

      while (unvisited.contains(cell)) {
        cell = sampleCell(cell.neighbors())
        val position = path.indexOf(cell)

        if (position > 0) {
          path.sliceInPlace(0, position+1)
        } else {
          path += cell
        }
      }

      for (i <- 0 to path.length - 2) {
        path(i).link(path(i + 1))
        unvisited -= path(i)
      }
    }

    grid
  }
}

