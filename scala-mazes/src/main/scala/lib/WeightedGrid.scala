package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._

class WeightedGrid(override val rows: Int, override val columns: Int) extends OrthogonalGrid(rows, columns) {
  var maximum: Int = 0

  override protected def prepareGrid(): Array[Array[GridCell]] = {
    var cells = Array.ofDim[GridCell](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      cells(i)(j) = new WeightedCell(i, j);
    }

    cells
  }

  override def id: String = "we"

  override def distances_=(d: Distances[MazeCell]) = {
    _distances = d
    val maxes = d.max()
    maximum = maxes._2
  }

  override def backgroundColorFor(cell: MazeCell): Color = {
    if (cell.weight > 1) {
      return new Color(255, 0, 0)
    }
    if (distances != null) {
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
