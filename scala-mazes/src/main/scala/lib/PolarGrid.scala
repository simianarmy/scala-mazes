package lib

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, RenderingHints}
import java.awt.geom.Arc2D
import lib.MazeCell._

/**
 * TODO: Support drawing arcs
 */
case class PolarGrid(override val rows: Int) extends Grid[PolarCell](rows, 1) {
  protected val grid = prepareGrid()

  configureCells()

  protected def prepareGrid(): Array[ArrayBuffer[PolarCell]] = {
    var cells = new Array[ArrayBuffer[PolarCell]](rows)
    val rowHeight = 1.0 / rows

    cells(0) = ArrayBuffer[PolarCell](new PolarCell(0, 0))

    for (i <- 1 until rows) {
      val radius: Float = i.toFloat / rows
      val circumference = 2 * Math.PI * radius
      val previousCount = cells(i - 1).length
      val estimatedCellWidth = circumference / previousCount
      val ratio = Math.round(estimatedCellWidth / rowHeight)
      val numCells = previousCount * ratio

      cells(i) = new ArrayBuffer[PolarCell](numCells.toInt)

      for (j <- 0 until numCells.toInt) {
        cells(i) += new PolarCell(i, j)
      }
    }
    cells
  }

  private def configureCells(): Unit = {
    eachCell(cell => {
      if (cell.row > 0) {
        cell.cw = getCell(cell.row, cell.column + 1)
        cell.ccw = getCell(cell.row, cell.column - 1)

        val ratio = grid(cell.row).length / grid(cell.row - 1).length

        getCell(cell.row - 1, cell.column / ratio) match {
          case parent: PolarCell if !parent.isNil  => {
            parent.outward += cell
            cell.inward = parent
          }
          case _ => ()
        }
      }
    })
  }

  override def id: String = "po"

  override def size(): Int = {
    grid.iterator.map(_.size).sum
  }

  override def eachRow(fn: (Iterator[PolarCell] => Unit)): Unit ={
    // collect cells into rows
    for (row <- 0 until rows) {
      fn(grid(row).iterator);
    }
  }

  def apply(row: Int): ArrayBuffer[PolarCell] = grid(row)

  def getCell(row: Int, column: Int): PolarCell = {
    try {
      grid(row)(column % grid(row).size)
    } catch {
      case e: Exception => MazeCell.nilCell[PolarCell]
    }
  }

  def cellAt(index: Int): PolarCell = {
    var counter = 0
    var found = false
    var cell: PolarCell = grid(0)(0)

    for (i <- 0 until rows; j <- 0 until grid(i).size) {
      if (!found && counter >= index) {
        cell = grid(i)(j)
        found = true
      }
      counter += 1
    }
    cell
  }

  def randomCell(): PolarCell = {
    val row = rand.nextInt(rows);
    val column = rand.nextInt(grid(row).length);

    getCell(row, column)
  }

  override def toString(): String = {
    s"PolarGrid $rows x $columns"
  }

  def toPng(cellSize: Int = 10, inset: Double = 0): BufferedImage = {
    val size = 2 * rows * cellSize
    val background = Color.WHITE
    val wall = Color.BLACK

    createPng(size + 1, size + 1, g => {
      val center = size / 2

      /**
       * Helpers or drawing arcs
       */
      // Return polar angle of any point relative to arc center.
      def angle0(x: Float, y: Float): Float = {
        return Math.toDegrees(Math.atan2(center - y, x - center)).toFloat
      }

      // Find the angular difference between a and b, -180 <= diff < 180.
      def angleDiff(a: Float, b: Float): Float = {
        var d = b - a
        while (d >= 180f) { d = d - 360; }
        while (d < -180f) { d = d + 360f }
        d
      }

      for (mode <- List('bgs, 'walls)) {
        eachCell((cell: PolarCell) => {
          if (cell.row != 0) {
            val theta = 2 * Math.PI / grid(cell.row).length
            val innerRadius = cell.row * cellSize
            val outerRadius = (cell.row + 1) * cellSize
            val thetaCcw = cell.column * theta
            val thetaCw = (cell.column + 1) * theta
            val ax = center + (innerRadius * Math.cos(thetaCcw)).toInt
            val ay = center + (innerRadius * Math.sin(thetaCcw)).toInt
            val bx = center + (outerRadius * Math.cos(thetaCcw)).toInt
            val by = center + (outerRadius * Math.sin(thetaCcw)).toInt
            val cx = center + (innerRadius * Math.cos(thetaCw)).toInt
            val cy = center + (innerRadius * Math.sin(thetaCw)).toInt
            val dx = center + (outerRadius * Math.cos(thetaCw)).toInt
            val dy = center + (outerRadius * Math.sin(thetaCw)).toInt
            //val aa: Float = angle0(ax, ay)
            //val ab: Float = angle0(bx, by)
            //val ac: Float = angle0(cx, cy)

            //println(List(ax, ay, cx, cy, dx, dy))
            //println("thetaCw: " + thetaCw)
            //println("thetaCcw: " + thetaCcw)
            //println("angleA: " + aa)
            //println("angleC: " + ac)
            //println("angleDiff " + angleDiff(aa, ac))

            if (mode == 'bgs) {
              val color = backgroundColorFor(cell)

              if (color != null) {
                g.setColor(color)
                g.fillPolygon(Array(ax, cx, dx), Array(ay, cy, dy), 3)
                g.fillPolygon(Array(ax, bx, dx), Array(ay, by, dy), 3)
              }
            } else {
              g.setColor(wall)
              if (!cell.isLinked(cell.inward)) {
                g.drawLine(ax, ay, cx, cy)
                //g.draw(new Arc2D.Float(ax, ay, innerRadius, innerRadius, aa, angleDiff(aa, ac), Arc2D.OPEN))
              }

              if (!cell.isLinked(cell.cw)) {
                g.drawLine(cx, cy, dx, dy)
                //g.draw(new Arc2D.Double(bx, by, innerRadius, innerRadius, ab, angleDiff(ab, angle0(dx, dy)), Arc2D.OPEN))
              }
            }
          }
        })
      }

      g.drawOval(0, 0, size, size)
    })

  }
}
