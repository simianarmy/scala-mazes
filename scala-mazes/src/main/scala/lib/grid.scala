/**
  * The core class for all mazes
  */
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke}
import java.awt.geom._

trait TextRenderer {
  def contentsOf(cell: Cell): String = " "
}

trait ImageRenderer {
  def backgroundColorFor(cell: Cell): Color = null
}

class Grid extends TextRenderer with ImageRenderer {
  var size = (0, 0)
  private var _grid: Array[Array[Cell]] = null

  def rows: Int = size._1
  def columns: Int = size._2

  def this(rows: Int, columns: Int) = {
    this()

    def prepareGrid(): Array[Array[Cell]] = {
      var grid = Array.ofDim[Cell](rows, columns);

      for (i <- 0 until rows; j <- 0 until columns) {
        grid(i)(j) = new Cell(i, j);
      }

      grid
    }

    def configureCells(): Unit = {
      for (i <- 0 until rows; j <- 0 until columns) {
        var cell = getCell(i, j)
        val row = cell.row;
        val column = cell.column;

        cell.north = getCell(row - 1, column);
        cell.south = getCell(row + 1, column);
        cell.west = getCell(row, column - 1);
        cell.east = getCell(row, column + 1);
      }
    }

    size = (rows, columns)
    _grid = prepareGrid()

    configureCells()
  }

  def getCell(row: Int, column: Int): Cell = {
    if (row < 0 || row >= rows) return null;
    if (column < 0 || column >= columns) return null;

    _grid(row)(column)
  }

  def randomCell(): Cell = {
    val r = scala.util.Random;

    val row = r.nextInt(rows);
    val column = r.nextInt(_grid(row).length);

    getCell(row, column)
  }

  def eachRow(fn: (Array[Cell] => Unit)) = {
    for (i <- 0 until rows) {
      fn(_grid(i));
    }
  }

  def eachCell(fn: (Cell => Unit)) = {
    eachRow(row => {
      for (i <- 0 until columns) {
        fn(row(i))
      }
    })
  }

  def cellIndex(cell: Cell): Int = cell.row * columns + cell.column

  override def toString(): String = {
    var res =
      rows + " x " + columns + "\n+" + ("---+" * columns) + "\n";

    eachRow(row => {
      var top = "|";
      var bottom = "+";

      for (i <- 0 until columns) {
        var c = if (row(i) == null) new Cell(-1, -1) else row(i);
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

    return canvas
  }
}
