import java.awt.Color

/**
  * A colored grid using Dijkstras distances
  */

object RGBUtil {
  var lights = Array(0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13,
    15, 17, 18, 20, 22, 24, 26, 28, 30, 32, 35, 37, 39, 42, 44, 47, 49, 52, 55,
    58, 60, 63, 66, 69, 72, 75, 78, 81, 85, 88, 91, 94, 97, 101, 104, 107, 111,
    114, 117, 121, 124, 127, 131, 134, 137, 141, 144, 147, 150, 154, 157, 160,
    163, 167, 170, 173, 176, 179, 182, 185, 188, 191, 194, 197, 200, 202, 205,
    208, 210, 213, 215, 217, 220, 222, 224, 226, 229, 231, 232, 234, 236, 238,
    239, 241, 242, 244, 245, 246, 248, 249, 250, 251, 251, 252, 253, 253, 254,
    254, 255, 255, 255, 255, 255, 255, 255, 254, 254, 253, 253, 252, 251, 251,
    250, 249, 248, 246, 245, 244, 242, 241, 239, 238, 236, 234, 232, 231, 229,
    226, 224, 222, 220, 217, 215, 213, 210, 208, 205, 202, 200, 197, 194, 191,
    188, 185, 182, 179, 176, 173, 170, 167, 163, 160, 157, 154, 150, 147, 144,
    141, 137, 134, 131, 127, 124, 121, 117, 114, 111, 107, 104, 101, 97, 94, 91,
    88, 85, 81, 78, 75, 72, 69, 66, 63, 60, 58, 55, 52, 49, 47, 44, 42, 39, 37,
    35, 32, 30, 28, 26, 24, 22, 20, 18, 17, 15, 13, 12, 11, 9, 8, 7, 6, 5, 4, 3,
    2, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
    0, 0, 0);

  def sineRGB(angle: Int): (Int, Int, Int) = {
    (lights((angle + 120) % 360), lights(angle), lights((angle + 240) % 360))
  }
}

class ColoredGrid(rows: Int, columns: Int, startColor: Color = Color.WHITE)
    extends DistanceGrid(rows, columns) {
  var maximum = 0
  private var _currColor = startColor
  private var angle = 0
  private var _r = scala.util.Random
  private var _color = Array(0, 0, 0)
  private var _count = 0

  override def distances_=(d: Distances): Unit = {
    _distances = d
    maximum = distances.max._2
  }

  override def backgroundColorFor(cell: Cell): Color = {
    val distance = distances.get(cell)

    if (distance == Distances.NotFound) return null

    val intensity = (maximum - distance).toFloat / maximum
    println("intensity " + intensity)
    /*
     * https://www.instructables.com/id/How-to-Make-Proper-Rainbow-and-Random-Colors-With-/ section
     * Step 5: Color Shifting is worth trying here
     */
    /*
    var a0 = _r.nextInt(240);
    _color(_count) = RGBUtil.lights(a0)
    var a1 = _r.nextInt(1)
    var a2 = if (a1 == 0) 1 else 0
    a2 = (a2 + _count + 1) % 3

    a1 = (_count + a1 + 1) % 3
    _color(a1) = RGBUtil.lights((a0 + 100) % 240)
    _color(a2) = 0
    _count = (_count + 1) % 3

    val r = _color(0)
    val g = _color(1)
    val b = _color(2)
    println("sine rgb " + r + "," + g + "," + b)

    val red = (r * intensity).toInt
    val green = (g * intensity).toInt
    val blue = (b * intensity).toInt

    new Color(r, g, b)
     */

    val dark = (255 * intensity).toInt
    val bright = 128 + (127 * intensity).toInt

    //_currColor = _currColor.darker()
    //if (_currColor.getRed() <= 0) {
    //_currColor = startColor
    //}

    new Color(dark, bright, dark)
  }
}
