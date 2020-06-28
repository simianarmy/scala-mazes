/**
 * The core class for all mazes
 */
package mazes

import mazes.Cell

class Grid {
  var rows : Int = 0;
  var columns : Int = 0;
  private var _grid : Array[Array[Cell]] = null;

  def this(rows : Int, columns : Int) = {
    this();
    this.rows = rows;
    this.columns = columns;
    this._grid = this.prepareGrid();

    this.configureCells;
  }

  def prepareGrid() : Array[Array[Cell]] = {
    var grid = Array.ofDim[Cell](this.rows, this.columns);

    for (i <- 0 until this.rows;  j<- 0 until this.columns) {
      grid(i)(j) = new Cell(i, j);
    }

    return grid;
  }

  def configureCells {
    for (i <- 0 until this.rows; j <- 0 until this.columns) {
      var cell = this.getCell(i, j)
      val row = cell.row;
      val column = cell.column;

      cell.north = this.getCell(row - 1, column);
      cell.south = this.getCell(row + 1, column);
      cell.west = this.getCell(row, column - 1);
      cell.east = this.getCell(row, column + 1);
    }
  }

  def getCell(row : Int, column : Int) : Cell = {
    if (row < 0 || row >= this.rows) return null;
    if (column < 0 || column >= this.columns) return null;

    return this._grid(row)(column);
  }

  def randomCell() : Cell = {
    val r = scala.util.Random;
    val row = r.nextInt(this.rows);
    val column = r.nextInt(this._grid(row).length);

    return this.getCell(row, column);
  }

  def eachRow(fn : (Array[Cell] => Any)) = {
    for (i <- 0 until this.rows) {
      fn(this._grid(i));
    }
  }

  def eachCell(fn : (Cell => Any)) = {
    for (i <- 0 until this.rows; j <- 0 until this.columns) {
      fn(this.getCell(i, j));
    }
  }

  override def toString() : String = {
    var res = "+" + ("---+" * this.columns) + "\n";

    this.eachRow(row => {
      var top = "|";
      var bottom = "+";

      for (i <- 0 until row.length) {
        var c = if (row(i) == null) new Cell(-1, -1) else row(i);
        var body = "   "; // 3 spaces
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
}
