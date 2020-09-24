/**
  * The core class for all mazes
  */
package lib

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, RenderingHints}
import java.awt.geom._

case class OrthogonalGrid(override val rows: Int, override val columns: Int) extends Grid[GridCell](rows, columns) with Randomizer {
  protected val grid = prepareGrid()

  configureCells()

  protected def prepareGrid(): Array[Array[GridCell]] = {
    var cells = Array.ofDim[GridCell](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      cells(i)(j) = new GridCell(i, j);
    }

    cells
  }

  private def configureCells(): Unit = {
    for (i <- 0 until rows; j <- 0 until columns) {
      var cell = getCell(i, j)

      if (cell != null) {
        val row = cell.row;
        val column = cell.column;

        cell.north = getCell(row - 1, column);
        cell.south = getCell(row + 1, column);
        cell.west = getCell(row, column - 1);
        cell.east = getCell(row, column + 1);
      }
    }
  }

  // Direct (unsafe) element accessor
  def apply(row: Int): Array[GridCell] = grid(row)

  def id: String = "ot"

  // Safe element accessor
  def getCell(row: Int, column: Int): GridCell = {
    if (row < 0 || row >= rows || column < 0 || column >= columns) null
    else grid(row)(column)
  }

  def cellAt(index: Int): GridCell = {
    val x = index / columns
    val y = index % columns

    getCell(x, y)
  }

  def randomCell(): GridCell = {
    val row = rand.nextInt(rows);
    val column = rand.nextInt(grid(row).length);

    getCell(row, column)
  }

  override def toString(): String = {
    // TODO: Imperative -> Functional
    var res =
      rows + " x " + columns + "\n+" + ("---+" * columns) + "\n";

    for (row <- grid) {
      var top = "|";
      var bottom = "+";

      row.foreach(rowCell => {
        var c = if (rowCell == null) new GridCell(-1, -1) else rowCell;
        var body = " " + contentsOf(c) + " "
        var eastBoundary = if (c.isLinked(c.east)) " " else "|";
        top += (body + eastBoundary);
        var southBoundary = if (c.isLinked(c.south)) "   " else "---";
        var corner = "+";
        bottom += (southBoundary + corner);
      });

      res += top + "\n";
      res += bottom + "\n";
    }

    res
  }

  def toPng(cellSize: Int = 10, inset: Double = 0): BufferedImage = {
    val dimensions = (cellSize * columns, cellSize * rows)
    val cellInset = (cellSize * inset).toInt
    val wall = Color.BLACK

    createPng(dimensions._1, dimensions._2, (g => {
      for (mode <- List('bgs, 'walls)) {
        eachCell(cell => {
          val x = cell.column * cellSize
          val y = cell.row * cellSize

          if (cellInset > 0) {
            toPngWithInset(g, cell, mode, cellSize, wall, x, y, cellInset)
          } else {
            toPngWithoutInset(g, cell, mode, cellSize, wall, x, y)
          }
        });
      }
    }))
  }

  def toPngWithoutInset(g: Graphics2D, cell: GridCell, mode: Symbol, cellSize: Int, wallColor: Color, x: Int, y: Int) = {
    val x1 = x
    val y1 = y
    val x2 = x1 + cellSize
    val y2 = y1 + cellSize
    val breadcrumbColor = Color.MAGENTA

    if (mode == 'bgs) {
      val color = backgroundColorFor(cell)

      if (color != null) {
        g.setColor(color)
        g.fillRect(x, y, x2, y2)
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

  def toPngWithInset(g: Graphics2D, cell: GridCell, mode: Symbol, cellSize: Int, wallColor: Color, x: Int, y: Int, inset: Int) = {
    val Array(x1, x2, x3, x4, y1, y2, y3, y4) = cellCoordinatesWithInset(x, y, cellSize, inset)

    if (mode == 'bgs) {
      // TODO: Implement me (page 270)
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
}
