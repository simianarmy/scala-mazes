package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import scala.reflect.{ClassTag, classTag}

class WeaveGrid(override val rows: Int, override val columns: Int) extends OrthogonalGrid[OverCell](rows,columns) with Randomizer {
  var underCells = List[UnderCell]()

  override def createCell[A <: GridCell : ClassTag](i: Int, j: Int): A = MazeCell.createCell[A](i, j, this)
  override def nilCell[A <: GridCell : ClassTag]: A = createCell(-1, -1)

  override def id: String = "wv"

  override def eachCell(fn: (OverCell => Unit)): Unit = {
    super.eachCell(fn)
    underCells.foreach(fn)
  }

  def tunnelUnder(overCell: OverCell): Unit = {
    underCells = underCells ::: List(new UnderCell(overCell))
  }

  override def toPng(size: Int = 10, inset: Double = 0.1): BufferedImage = {
    super.toPng(size, inset) //Math.max(inset, 0.1))
  }

  override def toPngWithInset(g: Graphics2D, cell: OverCell, mode: Symbol, cellSize: Int, wallColor: Color, x: Int, y: Int, inset: Int): Unit = {
    cell match {
      case uc: UnderCell => {
        val Array(x1, x2, x3, x4, y1, y2, y3, y4) = cellCoordinatesWithInset(x, y, cellSize, inset)

        if (mode == 'bgs) {
          val color = backgroundColorFor(cell)

          if (color != null) {
            g.setColor(color)

            if (uc.isVerticalPassage) {
              g.fillRect(x2, y1, x3 - x2, y2 - y1)
              g.fillRect(x2, y3, x3 - x2, y4 - y3)
            } else {
              g.fillRect(x1, y2, x2 - x1, y3 - y2)
              g.fillRect(x3, y2, x4 - x3, y3 - y2)
            }
          }
        } else {
          g.setColor(wallColor)

          if (uc.isVerticalPassage) {
            g.drawLine(x2, y1, x2, y2)
            g.drawLine(x3, y1, x3, y2)
            g.drawLine(x2, y3, x2, y4)
            g.drawLine(x3, y3, x3, y4)
          } else {
            g.drawLine(x1, y2, x2, y2)
            g.drawLine(x1, y3, x2, y3)
            g.drawLine(x3, y2, x4, y2)
            g.drawLine(x3, y3, x4, y3)
          }
        }
      }
      case c: OverCell => {
        super.toPngWithInset(g, c, mode, cellSize, wallColor, x, y, inset)
      }
    }
  }
}
