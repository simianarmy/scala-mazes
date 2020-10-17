package lib

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, RenderingHints}
import java.awt.geom.Arc2D
import lib.MazeCell._

class HemisphereCell(val hemisphere: Int, row: Int, column: Int) extends PolarCell(row, column)

class HemisphereGrid(val updown: Int, rows: Int) extends PolarGrid(rows) {

  override def prepareGrid(): Array[ArrayBuffer[PolarCell]] = {
    var cells = new Array[ArrayBuffer[PolarCell]](rows)
    val angularHeight = Math.PI / (2 * rows)

    cells(0) = ArrayBuffer[PolarCell](new HemisphereCell(updown, 0, 0))

    for (row <- 1 until rows) {
      val theta = (row + 1) * angularHeight
      val radius = Math.sin(theta)
      val circumference = 2 * Math.PI * radius

      val previousCount = cells(row - 1).length
      val estimatedCellWidth = circumference / previousCount
      val ratio = Math.round(estimatedCellWidth / angularHeight)

      val numCells = previousCount * ratio

      cells(row) = ArrayBuffer.tabulate[PolarCell](numCells.toInt) { col =>
        new HemisphereCell(updown, row, col)
      }
    }
    cells
  }

  def size(row: Int): Int = grid(row).length
}

class SphereGrid(rows: Int) extends Grid[HemisphereCell](rows, 1) {
  assert(rows % 2 == 0) // rows must be even
  val equator = rows / 2

  protected val grid = prepareGrid()

  configureCells()

  def prepareGrid(): Array[HemisphereGrid] = {
    Array(new HemisphereGrid(0, equator), new HemisphereGrid(1, equator))
  }

  private def configureCells(): Unit = {
    val belt = equator - 1

    for (index <- 0 until size(belt)) {
      val a = grid(0).getCell(belt, index)
      val b = grid(1).getCell(belt, index)
      a.outward += b
      b.outward += a
    }
  }

  def id: String = "sg"

  def getCell(row: Int, column: Int): HemisphereCell = getCell(0, row, column)

  def getCell(hemi: Int, row: Int, column: Int): HemisphereCell = grid(hemi).getCell(row, column).asInstanceOf[HemisphereCell]

  def size(row: Int): Int = grid(0).size(row)

  override def eachCell(fn: (HemisphereCell => Unit)): Unit = {
    grid( 0 ).eachCell(c => fn(c.asInstanceOf[HemisphereCell]));
    grid( 1 ).eachCell(c => fn(c.asInstanceOf[HemisphereCell]));
  }

  // TODO:
  def cellAt(index: Int): HemisphereCell = {
    getCell(0, 0)
  }

  def randomCell(): HemisphereCell = {
    RandomUtil.sample(grid).randomCell().asInstanceOf[HemisphereCell]
  }

  def toPng(idealSize: Int = 10, inset: Double = 0): BufferedImage = {
    val imgHeight = idealSize * rows
    val imgWidth = grid(0).size(equator - 1) * idealSize

    val background = Color.WHITE
    val wall = Color.BLACK

    createPng(imgWidth + 1, imgHeight + 1, g => {
      eachCell(cell => {
        g.setColor(wall)
        val rowSize = size(cell.row)
        val cellWidth = imgWidth.toFloat / rowSize

        var x1 = cell.column * cellWidth
        var x2 = x1 + cellWidth
        var y1 = cell.row * idealSize
        var y2 = y1 + idealSize

        // flip coordinates for the s. hemisphere
        if (cell.hemisphere > 0) {
          y1 = imgHeight - y1
          y2 = imgHeight - y2
        }

        x1 = x1.round.toInt
        y1 = y1.round.toInt
        x2 = x2.round.toInt
        y2 = y2.round.toInt

        if (cell.row > 0) {
          if (!cell.isLinked(cell.cw)) {
            g.drawLine(x2.toInt, y1, x2.toInt, y2)
          }
          if (!cell.isLinked(cell.inward)) {
            g.drawLine(x1.toInt, y1, x2.toInt, y1)
          }
        }

        if (cell.hemisphere == 0 && cell.row == equator - 1) {
          if (!cell.isLinked(cell.outward(0))) {
            g.drawLine(x1.toInt, y2, x2.toInt, y2)
          }
        }
      })
    })
  }
}
