package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke}

class PolarGrid(rows: Int, cols: Int) extends Grid(rows, cols) {
  override def toPng(cellSize: Int = 10): BufferedImage = {
    val size = 2 * rows * cellSize
    val background = Color.WHITE
    val wall = Color.BLACK

    val canvas =
      new BufferedImage(size + 1, size + 1, BufferedImage.TYPE_INT_RGB);
    // get Graphics2D for the image
    val g = canvas.createGraphics();
    g.setColor(background)
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
    g.setColor(wall)

    val center: Float = size / 2

    eachCell(cell => {
      println("cell " + cell)
      val theta = 2 * Math.PI / _grid(cell.row).length
      println("theta " + theta)
      val innerRadius = (cell.row + 1) * cellSize
      val outerRadius = (cell.row + 2) * cellSize
      val thetaCcw = (cell.column  + 1) * theta
      val thetaCw = (cell.column + 2) * theta
      val ax: Float = center + (innerRadius * Math.cos(thetaCcw)).toInt
      val ay: Float = center + (innerRadius * Math.sin(thetaCcw)).toInt
      val bx: Float = center + (outerRadius * Math.cos(thetaCcw)).toInt
      val by: Float = center + (outerRadius * Math.sin(thetaCcw)).toInt
      val cx: Float = center + (innerRadius * Math.cos(thetaCw)).toInt
      val cy: Float = center + (innerRadius * Math.sin(thetaCw)).toInt
      val dx: Float = center + (outerRadius * Math.cos(thetaCw)).toInt
      val dy: Float = center + (outerRadius * Math.sin(thetaCw)).toInt

      if (!cell.isLinked(cell.north)) {
        g.drawLine(ax.toInt, ay.toInt, cx.toInt, cy.toInt)
      }

      if (!cell.isLinked(cell.east)) {
        g.drawLine(cx.toInt, cy.toInt, dx.toInt, dy.toInt)
      }
    })

    val r = rows * cellSize
    val x = center - (r / 2);
    val y = center - (r / 2);

    //g.setColor(background)
    g.drawOval(x.toInt, y.toInt, r, r);

    g.dispose()
    canvas
  }
}
