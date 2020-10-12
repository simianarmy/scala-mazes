package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._

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

  def toPngWithoutInset(g: Graphics2D, cell: MazeCell, mode: Symbol, cellSize: Int, wallColor: Color, x: Int, y: Int) = {
    val x1 = x
    val y1 = y
    val x2 = x1 + cellSize
    val y2 = y1 + cellSize
    val breadcrumbColor = Color.MAGENTA

    if (mode == 'bgs) {
      val color = backgroundColorFor(cell)

      if (color != null) {
        g.setColor(color)
        g.fillRect(x1, y1, cellSize, cellSize)
      }
    } else {
      g.setColor(wallColor)

      if (cell.north == null) {
        g.draw(new Line2D.Double(x1, y1, x2, y1))
      }
      if (cell.west == null) {
        g.draw(new Line2D.Double(x1, y1, x1, y2))
      }
      if (!cell.isLinked(cell.east)) {
        g.draw(new Line2D.Double(x2, y1, x2, y2))
      }
      if (!cell.isLinked(cell.south)) {
        g.draw(new Line2D.Double(x1, y2, x2, y2))
      }
    }
  }

  def toPngWithInset(g: Graphics2D, cell: MazeCell, mode: Symbol, cellSize: Int, wallColor: Color, x: Int, y: Int, inset: Int) = {
    val Array(x1, x2, x3, x4, y1, y2, y3, y4) = cellCoordinatesWithInset(x, y, cellSize, inset)

    if (mode == 'bgs) {
      val color = backgroundColorFor(cell)

      if (color != null) {
        g.setColor(color)

        if (cell.isLinked(cell.north)) {
          g.fillRect(x2, y1, x3 - x2, y2 - y1)
        }
        if (cell.isLinked(cell.south)) {
          g.fillRect(x2, y3, x3 - x2, y4 - y3)
        }
        if (cell.isLinked(cell.west)) {
          g.fillRect(x1, y2, x2 - x1, y3 - y2)
        }
        if (cell.isLinked(cell.east)) {
          g.fillRect(x3, y2, x4 - x3, y3 - y2)
        }
        g.fillRect(x2, y2, x3 - x2, y3 - y2) // C
      }
    } else {
      g.setColor(wallColor)

      if (cell.isLinked(cell.north)) {
        g.drawLine(x2, y1, x2, y2)
        g.drawLine(x3, y1, x3, y2)
      } else {
        g.drawLine(x2, y2, x3, y2)
      }

      if (cell.isLinked(cell.south)) {
        g.drawLine(x2, y3, x2, y4)
        g.drawLine(x3, y3, x3, y4)
      } else {
        g.drawLine(x2, y3, x3, y3)
      }

      if (cell.isLinked(cell.west)) {
        g.drawLine(x1, y2, x2, y2)
        g.drawLine(x1, y3, x2, y3)
      } else {
        g.drawLine(x2, y2, x2, y3)
      }

      if (cell.isLinked(cell.east)) {
        g.drawLine(x3, y2, x4, y2)
        g.drawLine(x3, y3, x4, y3)
      } else {
        g.drawLine(x3, y2, x3, y3)
      }
    }
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



