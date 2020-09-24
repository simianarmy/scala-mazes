package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._

case class HexGrid(override val rows: Int, override val columns: Int) extends Grid[HexCell](rows,columns) with Randomizer {
  protected val grid = prepareGrid()

  configureCells()

  protected def prepareGrid(): Array[Array[HexCell]] = {
    var cells = Array.ofDim[HexCell](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      cells(i)(j) = new HexCell(i, j);
    }

    cells
  }

  private def configureCells(): Unit = {
    eachCell(cell => {
      val row = cell.row
      val col = cell.column
      val diags = if (col % 2 == 0) (row - 1, row) else (row, row + 1)

      cell.northwest = getCell(diags._1, col - 1)
      cell.north = getCell(row -1, col)
      cell.northeast = getCell(diags._1, col + 1)
      cell.southwest = getCell(diags._2, col - 1)
      cell.south = getCell(row + 1, col)
      cell.southeast = getCell(diags._2, col + 1)
    })
  }

  override def id: String = "hx"

  // Direct (unsafe) element accessor
  def apply(row: Int): Array[HexCell] = grid(row)

  // Safe element accessor
  def getCell(row: Int, column: Int): HexCell = {
    if (row < 0 || row >= rows || column < 0 || column >= columns) null
    else grid(row)(column)
  }

  def cellAt(index: Int): HexCell = {
    val x = index / columns
    val y = index % columns

    getCell(x, y)
  }

  def randomCell(): HexCell = {
    val row = rand.nextInt(rows);
    val column = rand.nextInt(grid(row).length);

    getCell(row, column)
  }

  def toPng(size: Int = 10, inset: Double = 0) = {
    val aSize = size / 2.0
    val bSize = size * Math.sqrt(3) / 2.0
    val width = size * 2
    val height = bSize * 2

    val imgWidth: Int = (3 * aSize * columns + aSize + 0.5).toInt
    val imgHeight: Int = (height * rows + bSize + 0.5).toInt
    val wall = Color.BLACK;

    createPng(imgWidth, imgHeight, (g) => {
        List('bg, 'wall).foreach(mode => {
          eachCell(cell => {
            val cx = size + 3 * cell.column * aSize
            var cy = bSize + cell.row * height
            if (cell.column % 2 != 0) {
              cy = cy + bSize
            }

            // f/n = far/near
            // n/s/e/w = polar directions
            val xFw = (cx - size).toInt
            val xNw = (cx - aSize).toInt
            val xNe = (cx + aSize).toInt
            val xFe = (cx + size).toInt

            // m = middle
            val yN = (cy - bSize).toInt
            val yM = cy.toInt
            val yS = (cy + bSize).toInt

            if (mode == 'bg) {
              val color = backgroundColorFor(cell)

              if (color != null) {
                g.setColor(color)
                g.fillPolygon(Array(xFw, xNw, xNe, xFe, xNe, xNw), Array(yM, yN, yN, yM, yS, yS), 6)
              }
            } else {
              g.setColor(wall)

              if (cell.southwest == null) {
                g.draw(new Line2D.Double(xFw, yM, xNw, yS))
              }
              if (cell.northwest == null) {
                g.draw(new Line2D.Double(xFw, yM, xNw, yN))
              }
              if (cell.north == null) {
                g.draw(new Line2D.Double(xNw, yN, xNe, yN))
              }
              if (!cell.isLinked(cell.northeast)) {
                g.draw(new Line2D.Double(xNe, yN, xFe, yM))
              }
              if (!cell.isLinked(cell.southeast)) {
                g.draw(new Line2D.Double(xFe, yM, xNe, yS))
              }
              if (!cell.isLinked(cell.south)) {
                g.draw(new Line2D.Double(xNe, yS, xNw, yS))
              }
            }
          })
        })
    })
  }
}
