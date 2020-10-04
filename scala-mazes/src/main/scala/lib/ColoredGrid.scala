package lib

import java.awt.Color

/**
  * A colored grid using Dijkstras distances
  */

class ColoredGrid(rows: Int, columns: Int, startColor: Color = Color.WHITE)
    extends DistanceGrid(rows, columns) {
  var maximum = 0
  var sineColors = new SineWaveColors
  var colorRGB = Array(0, 0, 0)
  var colorStep = Array[Long](0, 0, 0)
  var lightsIdx = 0
  val MaxLightsIdx = 120
  val FadeColors = false

  def cellIndex(cell: MazeCell): Int = cell.row * columns + cell.column

  override def id: String = "co"

  override def distances_=(d: Distances[MazeCell]): Unit = {
    _distances = d
    maximum = distances.max._2
  }

  override def backgroundColorFor(cell: MazeCell): Color = {
    val distance = distances.get(cell)

    if (distance == Distances.NotFound) return null

    val intensity = (maximum - distance).toFloat / maximum
    val dark = (255 * intensity).toInt
    val bright = 128 + (127 * intensity).toInt

    new Color(dark, bright, dark)
  }
}
