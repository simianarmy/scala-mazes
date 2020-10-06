package lib

import java.awt.{Color}

class WeightedGrid(override val rows: Int, override val columns: Int) extends OrthogonalGrid[WeightedCell](rows, columns) {
  var maximum = 0

  override def id: String = "we"

  override def distances_=(d: Distances[MazeCell]): Unit = {
    _distances = d
    maximum = distances.max._2
  }

  override def backgroundColorFor(cell: MazeCell): Color = {
    println("bg color for " + cell)
    if (cell.weight > 1) {
      return new Color(255, 0, 0)
    } else if (distances != null) {
      val distance = distances.get(cell)

      if (distance == Distances.NotFound) {
        return null
      }
      val intensity = 64 + 191 * (maximum - distance) / maximum
      return new Color(intensity, intensity, 0)
    }
    null
  }
}
