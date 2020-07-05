import java.awt.Color

/**
  * A colored grid using Dijkstras distances
  */

class ColoredGrid(rows: Int, columns: Int) extends DistanceGrid(rows, columns) {
  var maximum = 0

  override def distances_=(d: Distances): Unit = {
    _distances = d
    maximum = distances.max._2
  }

  override def backgroundColorFor(cell: Cell): Color = {
    val distance = distances.get(cell)

    if (distance == Distances.NotFound) return null
    val intensity = (maximum - distance).toFloat / maximum
    val dark = (255 * intensity).toInt
    val bright = 128 + (127 * intensity).toInt

    new Color(dark, bright, dark)
  }
}
