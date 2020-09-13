package lib

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, RenderingHints}
import java.awt.geom.Arc2D

/**
  * TODO: Support drawing arcs
  */
class PolarGrid(rows: Int) extends Grid {
  type CellType = PolarCell

  var grid: ArrayBuffer[ArrayBuffer[PolarCell]] = prepareGrid()
  var size = (rows, 1)

  def prepareGrid(): ArrayBuffer[ArrayBuffer[PolarCell]] = {
    var cells = new ArrayBuffer[ArrayBuffer[PolarCell]](rows)
    val rowHeight = 1.0 / rows

    cells += ArrayBuffer(new PolarCell(0, 0))

    for (i <- 1 until rows) {
      val radius: Float = i.toFloat / rows
      val circumference = 2 * Math.PI * radius
      val previousCount = cells(i - 1).length
      val estimatedCellWidth = circumference / previousCount
      val ratio = Math.round(estimatedCellWidth / rowHeight)
      val numCells = previousCount * ratio

      cells += new ArrayBuffer[PolarCell](numCells.toInt)

      for (j <- 0 until numCells.toInt) {
        cells(i) += new PolarCell(i, j)
      }
    }

    cells
  }

  def configureCells(): Unit = {
    eachCell((cell: PolarCell) => {
      if (cell.row > 0) {
        cell.cw = getCell(cell.row, cell.column + 1)
        cell.ccw = getCell(cell.row, cell.column - 1)

        val ratio = grid(cell.row).length / grid(cell.row - 1).length
        val parent = grid(cell.row - 1)(cell.column / ratio)

        parent.outward += cell
        cell.inward = parent
      }
    })
  }

  def numCells: Int = {
    //TODO
    rows
  }

  def getCell(row: Int, column: Int): PolarCell = {
    // TODO
    grid(row)(column)
  }

  def randomCell(): PolarCell = {
    // TODO
    getCell(0, 0)
  }

  def eachRow(fn: (Iterator[PolarCell] => Unit)) = {
    for (i <- 0 until grid.length) {
      fn(grid(i).iterator);
    }
  }

  def eachCell(fn: (PolarCell => Unit)) = {
    eachRow((row: Iterator[PolarCell]) => {
      row.foreach(fn)
    })
  }

  def toPng(cellSize: Int = 10): BufferedImage = {
    val size = 2 * rows * cellSize
    val background = Color.WHITE
    val wall = Color.BLACK

    val canvas =
      new BufferedImage(size + 1, size + 1, BufferedImage.TYPE_INT_RGB)
    // get Graphics2D for the image
    val g = canvas.createGraphics()
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(background)
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)
    g.setColor(wall)

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

    eachCell((cell: PolarCell) => {
      println("drawing for cell " + cell)
      println("row length: " + grid(cell.row).length)
      val theta = 2 * Math.PI / grid(cell.row).length
      val innerRadius = cell.row * cellSize
      val outerRadius = (cell.row + 1) * cellSize
      val thetaCcw = cell.column * theta
      val thetaCw = (cell.column + 1) * theta
      val ax = center + (innerRadius * Math.cos(thetaCcw)).toInt
      val ay = center + (innerRadius * Math.sin(thetaCcw)).toInt
      //val bx = center + (outerRadius * Math.cos(thetaCcw)).toInt
      //val by = center + (outerRadius * Math.sin(thetaCcw)).toInt
      val cx = center + (innerRadius * Math.cos(thetaCw)).toInt
      val cy = center + (innerRadius * Math.sin(thetaCw)).toInt
      val dx = center + (outerRadius * Math.cos(thetaCw)).toInt
      val dy = center + (outerRadius * Math.sin(thetaCw)).toInt
      //val aa: Float = angle0(ax, ay)
      //val ab: Float = angle0(bx, by)
      //val ac: Float = angle0(cx, cy)

      println(List(ax, ay, cx, cy, dx, dy))
      //println("thetaCw: " + thetaCw)
      //println("thetaCcw: " + thetaCcw)
      //println("angleA: " + aa)
      //println("angleC: " + ac)
      //println("angleDiff " + angleDiff(aa, ac))

      if (!cell.isLinked(cell.north)) {
        println("drawing north")
        g.drawLine(ax, ay, cx, cy)
        //g.draw(new Arc2D.Float(ax, ay, innerRadius, innerRadius, aa, angleDiff(aa, ac), Arc2D.OPEN))
      }

      if (!cell.isLinked(cell.east)) {
        println("drawing east")
        g.drawLine(cx, cy, dx, dy)
        //g.draw(new Arc2D.Double(bx, by, innerRadius, innerRadius, ab, angleDiff(ab, angle0(dx, dy)), Arc2D.OPEN))
      }
    })

    g.drawOval(0, 0, size, size)

    g.dispose()
    canvas
  }
}
