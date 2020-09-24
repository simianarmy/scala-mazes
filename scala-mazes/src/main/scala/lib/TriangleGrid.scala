package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._

case class TriangleGrid(override val rows: Int, override val columns: Int) extends Grid[TriangleCell](rows,columns) with Randomizer {
  protected val grid = prepareGrid()

  configureCells()

  protected def prepareGrid(): Array[Array[TriangleCell]] = {
    var cells = Array.ofDim[TriangleCell](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      cells(i)(j) = new TriangleCell(i, j);
    }

    cells
  }

  private def configureCells(): Unit = {
    eachCell(cell => {
      val row = cell.row
      val col = cell.column

      cell.west = getCell(row, col - 1)
      cell.east = getCell(row, col + 1)

      if (cell.isUpright) {
        cell.south = getCell(row + 1, col)
      } else {
        cell.north = getCell(row - 1, col)
      }
    })
  }

  override def id: String = "tr"

  // Direct (unsafe) element accessor
  def apply(row: Int): Array[TriangleCell] = grid(row)

  // Safe element accessor
  def getCell(row: Int, column: Int): TriangleCell = {
    if (row < 0 || row >= rows || column < 0 || column >= columns) null
    else grid(row)(column)
  }

  def cellAt(index: Int): TriangleCell = {
    val x = index / columns
    val y = index % columns

    getCell(x, y)
  }

  def randomCell(): TriangleCell = {
    val row = rand.nextInt(rows);
    val column = rand.nextInt(grid(row).length);

    getCell(row, column)
  }

  def toPng(size: Int = 16, inset: Double = 0): BufferedImage = {
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
