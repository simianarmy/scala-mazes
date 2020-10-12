package lib

import scala.reflect.runtime._
import scala.reflect.{ClassTag, classTag}
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color }
import java.awt.geom._
import lib.MazeCell._

object Cell3D {
  def nilCell: Cell3D = new Cell3D(-1, -1, -1)
}

class Cell3D(val level: Int, row: Int, column: Int) extends GridCell(row, column) {
  var _up: Option[Cell3D] = None
  var _down: Option[Cell3D] = None

  def up = _up.getOrElse(nilCell)
  def down = _down.getOrElse(nilCell)
  def up_=(that: Cell3D) = _up = Some(that)
  def down_=(that: Cell3D) = _down = Some(that)

  override def neighbors: List[MazeCell] = {
    super.neighbors ::: List(up, down).filterNot(_.isNil)
  }
}

case class Grid3D(levels: Int, override val rows: Int, override val columns: Int) extends Grid[Cell3D](rows, columns) {
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
      case e: ArrayIndexOutOfBoundsException => nilCell
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

  def toPng(cellSize: Int = 10, inset: Double = 0): BufferedImage = {
    val dimensions = (cellSize * columns, cellSize * rows)
    val cellInset = (cellSize * inset).toInt
    val wall = Color.BLACK

    createPng(dimensions._1, dimensions._2, (g => {
    }))
  }
}
