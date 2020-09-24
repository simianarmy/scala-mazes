package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}

trait ImageRenderer {
  def toPng(cellSize: Int = 10, inset: Double = 0): BufferedImage
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

  // helper for toPngWithInset
  def cellCoordinatesWithInset(x: Int, y: Int, cellSize: Int, inset: Int): Array[Int] = {
    val x1 = x
    val x2 = x1 + inset
    val x4 = x + cellSize
    val x3 = x4 - inset

    val y1 = y
    val y2 = y1 + inset
    val y4 = y + cellSize
    val y3 = y4 - inset

    Array[Int](x1, x2, x3, x4, y1, y2, y3, y4)
  }
}



