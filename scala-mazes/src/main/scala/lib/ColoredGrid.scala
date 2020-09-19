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

  override def distances_=(d: Distances[MazeCell]): Unit = {
    _distances = d
    maximum = distances.max._2
  }

  override def backgroundColorFor(cell: MazeCell): Color = {
    val distance = distances.get(cell)

    if (distance == Distances.NotFound) return null

    val intensity = (maximum - distance).toFloat / maximum

    /*
     * algorithm from mazes book
    val dark = (255 * intensity).toInt
    val bright = 128 + (127 * intensity).toInt
    new Color(dark, bright, dark)
     */

    // Try to generate a rainbow or something more eye pleasing than above
    // using getNextColor will return a pretty random color
    /*
    val nextColor = sineColors.getNextColor()

    for (i <- 0 to 2) {
      colorRGB(i) = (nextColor(i) * intensity).toInt
      lightsIdx = 0
    }
    val c = new Color(
      255 - (colorRGB(0) >> 8),
      255 - (colorRGB(1) >> 8),
      255 - (colorRGB(2) >> 8)
    )
     */

    // Some random code for fading to a color
    /*
    lightsIdx = cellIndex(cell)
    if (lightsIdx < MaxLightsIdx) {
      for (i <- 0 to 2) {
        colorStep(i) = (nextColor(i).toLong - colorRGB(i)) / 255
        colorRGB(i) = ((colorRGB(i) + colorStep(i).toInt * RGBUtil.lights(
          lightsIdx
        )) * intensity).toInt
      }
    }
     */

    // sineRGB will produce a nice repeating rainbow gradient
    val (r, g, b) = RGBUtil.sineRGB(cellIndex(cell))
    colorRGB(0) = 255 - (r * intensity).toInt
    colorRGB(1) = 255 - (g * intensity).toInt
    colorRGB(2) = 255 - (b * intensity).toInt
    //println(cellIndex(cell) + " c: " + colorRGB.toString)

    new Color(colorRGB(0), colorRGB(1), colorRGB(2))
  }
}
