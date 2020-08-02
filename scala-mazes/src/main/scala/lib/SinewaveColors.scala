/**
  * Class to help display a rainbow of colors
  * https://www.instructables.com/id/How-to-Make-Proper-Rainbow-and-Random-Colors-With-/ section
  * Step 5: Color Shifting
  */
package lib

class SineWaveColors {
  var color = Array(0, 0, 0)
  var nextColor = Array(0, 0, 0)
  var count = 0
  var a0 = 0
  var a1 = 0
  var a2 = 0
  val r = scala.util.Random;

  def getNextColor(): Array[Int] = {
    a0 = r.nextInt(240)
    nextColor(count) = RGBUtil.lights(a0) << 8
    a1 = r.nextInt(1)
    a2 = if (a1 == 0) 1 else 0
    a2 = (a2 + count + 1) % 3
    a1 = (a1 + count + 1) % 3
    nextColor(a1) = RGBUtil.lights((a0 + 100) % 240) << 8
    nextColor(a2) = 0
    count = (count + 1) % 3

    nextColor
  }
}

