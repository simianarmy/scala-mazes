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

    for (row <- 0 until rows) {
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

  //TODO
  override def eachCell(fn: (HemisphereCell => Unit)): Unit = {
    iterator().filterNot(c => c.isNil).foreach(fn)
  }

  def cellAt(index: Int): HemisphereCell = {
    getCell(0, 0)
  }

  def randomCell(): HemisphereCell = {
    RandomUtil.sample(grid).randomCell().asInstanceOf[HemisphereCell]
  }

  def toPng(cellSize: Int = 10, inset: Double = 0): BufferedImage = {
    val size = 2 * rows * cellSize
    val background = Color.WHITE
    val wall = Color.BLACK

    createPng(size + 1, size + 1, g => {
    })
  }
}
