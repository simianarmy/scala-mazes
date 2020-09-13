/**
  * The core class for all mazes
  */
package lib

import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, RenderingHints}
import java.awt.geom._

class OrthogonalGrid(val rows: Int, val columns: Int) extends Grid with TextRenderer[GridCell] with ImageRenderer[GridCell] {
  type CellType = GridCell

  var size = (rows, columns)
  var _grid: Array[Array[CellType]] = prepareGrid()
  val r = scala.util.Random;

  configureCells()

  private def prepareGrid(): Array[Array[CellType]] = {
    var grid = Array.ofDim[CellType](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      grid(i)(j) = new GridCell(i, j);
    }

    grid
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

  def numCells: Int = rows * columns

  def getCell(row: Int, column: Int): CellType = {
    if (row < 0 || row >= rows) return null;
    if (column < 0 || column >= columns) return null;

    _grid(row)(column)
  }

  def randomCell(): CellType = {
    val row = r.nextInt(rows);
    val column = r.nextInt(_grid(row).length);

    getCell(row, column)
  }

  def eachRow(fn: (Iterator[CellType] => Unit)) = {
    for (i <- 0 until rows) {
      fn(_grid(i).iterator);
    }
  }

  def eachCell(fn: (CellType => Unit)) = {
    eachRow(row => {
      row.filter(_ != null).foreach(fn)
    })
  }

  override def toString(): String = {
    var res =
      rows + " x " + columns + "\n+" + ("---+" * columns) + "\n";

    eachRow(it => {
      var top = "|";
      var bottom = "+";

      while (it.hasNext) {
        val rowCell = it.next()
        var c = if (rowCell == null) new CellType(-1, -1) else rowCell;
        var body = " " + contentsOf(c) + " "
        var eastBoundary = if (c.isLinked(c.east)) " " else "|";
        top += (body + eastBoundary);
        var southBoundary = if (c.isLinked(c.south)) "   " else "---";
        var corner = "+";
        bottom += (southBoundary + corner);
      };

      res += top + "\n";
      res += bottom + "\n";
    });
    return res;
  }

  def toPng(cellSize: Int = 10): BufferedImage = {
    val size = (cellSize * columns, cellSize * rows);

    val background = Color.WHITE;
    val wall = Color.BLACK;
    val breadcrumbColor = Color.MAGENTA;

    val canvas =
      new BufferedImage(size._1 + 1, size._2 + 1, BufferedImage.TYPE_INT_RGB);
    // get Graphics2D for the image
    val g = canvas.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    // clear background
    g.setColor(background)
    g.fillRect(0, 0, canvas.getWidth, canvas.getHeight)

    // enable anti-aliased rendering prettier lines and circles)(
    g.setRenderingHint(
      java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON
    )
    g.setStroke(new BasicStroke()) // reset to default

    for (mode <- List("backgrounds", "walls")) {
      eachCell(cell => {
        val x1 = cell.column * cellSize;
        val y1 = cell.row * cellSize;
        val x2 = (cell.column + 1) * cellSize;
        val y2 = (cell.row + 1) * cellSize;

        if (mode == "backgrounds") {
          val color = backgroundColorFor(cell)
          if (color != null) {
            g.setColor(color)
          } else {
            g.setColor(background)
          }
          g.fillRect(x1, y1, (x2 - x1), (y2 - y1))

          // optional decoration for a cell
          val cellText = contentsOf(cell)
          if (cellText != null) {
            //println("cell text " + cellText)
            //g.drawString(cellText, x1 + (x2 - x1) / 3, y1 + (y2 - y1))
            //g.setColor(breadcrumbColor)
            //g.fillOval(x1 + 1, y1 + 1, 8, 8)
          }
        } else {
          g.setColor(wall)

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
      });
    }

    g.dispose()
    canvas
  }

  def deadends(): ArrayBuffer[CellType] = {
    var list = new ArrayBuffer[CellType]()

    eachCell(cell => {
      if (cell.getLinks().size == 1) {
        list += cell
      }
    })

    list
  }
}