package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}

trait ImageRenderer {
  def toPng(cellSize: Int = 10): BufferedImage
  def backgroundColorFor(cell: MazeCell): Color = null
  def createPng(width: Int, height: Int, op: => (Graphics2D => Unit)): BufferedImage = {
    val background = Color.WHITE;
    val canvas =
      new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);
    val g = canvas.createGraphics();

    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(background)
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    try {
      op(g)
      } finally {
        g.dispose()
      }
      canvas
  }
}



