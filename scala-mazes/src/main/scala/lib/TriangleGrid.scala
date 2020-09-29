package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._
import lib.MazeCell._

class TriangleGrid(override val rows: Int, override val columns: Int) extends OrthogonalGrid[TriangleCell](rows,columns) with Randomizer {

  private def configureCells(): Unit = {
    eachCell(cell => {
      val row = cell.row
      val col = cell.column

      cell.west = cellOrNil(getCell(row, col - 1))
      cell.east = cellOrNil(getCell(row, col + 1))

      if (cell.isUpright) {
        cell.south = cellOrNil(getCell(row + 1, col))
      } else {
        cell.north = cellOrNil(getCell(row - 1, col))
      }
    })
  }

  override def id: String = "tr"

  override def toPng(size: Int = 16, inset: Double = 0): BufferedImage = {
    val halfWidth = size / 2.0
    val height = size * Math.sqrt(3) / 2.0
    val halfHeight = height / 2.0
    val imgWidth = (size * (columns + 1) / 2.0).toInt
    val imgHeight = (height * rows).toInt
    val wall = Color.BLACK

    createPng(imgWidth, imgHeight, (g) => {
      List('bg, 'walls).foreach(mode => {
        eachCell(cell => {
          val cx = halfWidth + cell.column * halfWidth
          val cy = halfHeight + cell.row * height

          val westX = (cx - halfWidth).toInt
          val midX = cx.toInt
          val eastX = (cx + halfWidth).toInt
          var apexY = 0
          var baseY = 0

          if (cell.isUpright) {
            apexY = (cy - halfHeight).toInt
            baseY = (cy + halfHeight).toInt
          } else {
            apexY = (cy + halfHeight).toInt
            baseY = (cy - halfHeight).toInt
          }

          if (mode == 'bg) {
            val color = backgroundColorFor(cell)

            if (color != null) {
              g.setColor(color)
              g.drawPolygon(Array(westX, midX, eastX), Array(baseY, apexY, baseY), 3)
            }
          } else {
            g.setColor(wall)

            if (cell.west == null) {
              g.drawLine(westX, baseY, midX, apexY)
            }

            if (!cell.isLinked(cell.east)) {
              g.drawLine(eastX, baseY, midX, apexY)
            }

            val noSouth = cell.isUpright && (cell.south == null)
            val notLinked = !cell.isUpright && !cell.isLinked(cell.north)

            if (noSouth || notLinked) {
              g.drawLine(eastX, baseY, westX, baseY)
            }
          }
        })
      })
    })
  }
}
