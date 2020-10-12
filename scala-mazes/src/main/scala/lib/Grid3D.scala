package lib

import scala.reflect.runtime._
import scala.reflect.{ClassTag, classTag}
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color }
import java.awt.geom._

object Cell3D {
  def nilCell: Cell3D = new Cell3D(-1, -1, -1)
}

class Cell3D(val level: Int, row: Int, column: Int) extends GridCell(row, column) {
  var _up: Option[Cell3D] = None
  var _down: Option[Cell3D] = None

  def up = _up.getOrElse(Cell3D.nilCell)
  def down = _down.getOrElse(Cell3D.nilCell)
  def up_=(that: Cell3D) = _up = Some(that)
  def down_=(that: Cell3D) = _down = Some(that)

  override def neighbors: List[MazeCell] = {
    super.neighbors ::: List(up, down).filterNot(_.isNil)
  }

  override def toString: String = s"[Cell3D $level, $row, $column]"
}

class Grid3D(levels: Int, override val rows: Int, override val columns: Int) extends Grid[Cell3D](rows, columns) {
  protected val grid = prepareGrid

  configureCells()

  protected def prepareGrid: Array[Array[Array[Cell3D]]] =
    Array.tabulate(levels, rows, columns)((l, i, j) => new Cell3D(l, i, j))

  private def configureCells(): Unit = {
    eachCell(cell => {
      val level = cell.level
      val row = cell.row
      val column = cell.column

      cell.north = getCell(level, row - 1, column)
      cell.south = getCell(level, row + 1, column)
      cell.west = getCell(level, row, column - 1)
      cell.east = getCell(level, row, column + 1)
      cell.down = getCell(level - 1, row, column)
      cell.up = getCell(level + 1, row, column)
    })
  }

  // Direct (unsafe) element accessor
  //def apply(row: Int): Array[A] = grid(row)

  override def id: String = "3d"

  override def size: Int = levels * rows * columns

  // Most be defined for children of Grid
  def getCell(row: Int, column: Int): Cell3D = getCell(0, row, column)

  // level element accessor
  def getCell(level: Int, row: Int, column: Int): Cell3D = {
    try {
      grid(level)(row)(column)
    } catch {
      case e: ArrayIndexOutOfBoundsException => Cell3D.nilCell
    }
  }

  def cellAt(index: Int): Cell3D = {
    val level = index / (rows * columns)
    val levelIndex = index % (rows * columns)
    val x = levelIndex / columns
    val y = levelIndex % columns

    getCell(level, x, y)
  }

  def randomCell(): Cell3D = {
    val level = rand.nextInt(levels)
    val row = rand.nextInt(grid(level).length)
    val column = rand.nextInt(grid(level)(row).length)

    getCell(level, row, column)
  }

  def eachLevel(fn: (Iterator[Array[Cell3D]] => Unit)): Unit = {
    for (level <- 0 until levels) {
      fn(grid(level).iterator)
    }
  }

  override def eachRow(fn: (Iterator[Cell3D] => Unit)): Unit = {
    eachLevel(rows => {
      for (row <- rows) {
        fn(row.iterator)
      }
    })
  }

  override def toString(): String = {
    var res =
      rows + " x " + columns + "\n+" + ("---+" * columns) + "\n";

    for (level <- 0 until levels) {
      for (row <- grid(level)) {
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
    }

    res
  }

  def toPng(cellSize: Int = 0, inset: Double = 0): BufferedImage = toPng(cellSize, inset, 0)

  def toPng(cellSize: Int, inset: Double, margin: Double): BufferedImage = {
    val _inset = (cellSize * inset).toInt
    val _margin = if (margin > 0) margin else cellSize / 2
    val gridWidth = cellSize * columns
    val gridHeight = cellSize * rows
    val imgWidth = (gridWidth * levels + (levels - 1) * _margin).toInt
    val imgHeight = gridHeight
    val wall = Color.BLACK
    val background = color
    val arrow = Color.RED

    createPng(imgWidth + 1, imgHeight + 1, (g => {
      for (mode <- List('bgs, 'walls)) {
        eachCell(cell => {
          val x = (cell.level * (gridWidth + _margin) + cell.column * cellSize).toInt
          val y = (cell.row * cellSize).toInt

          if (_inset > 0) {
            toPngWithInset(g, cell, mode, cellSize, wall, x, y, _inset)
          } else {
            toPngWithoutInset(g, cell, mode, cellSize, wall, x, y)
          }

          if (mode == 'walls) {
            val midX = x + cellSize / 2
            val midY = y + cellSize / 2
            g.setColor(wall)

            if (cell.isLinked(cell.down)) {
              g.setColor(arrow)
              g.drawLine(midX - 3, midY, midX - 1, midY + 2)
              g.drawLine(midX - 3, midY, midX - 1, midY - 2)
            }

            if (cell.isLinked(cell.up)) {
              g.setColor(arrow)
              g.drawLine(midX + 3, midY, midX + 1, midY + 2)
              g.drawLine(midX + 3, midY, midX + 1, midY - 2)
            }
          }
        })
      }
    }))
  }
}
