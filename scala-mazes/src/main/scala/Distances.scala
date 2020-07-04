/**
  * @class Distances class
  * Used for Dijkstra's algorithm
  */

object Distances {
  val NotFound = -1
}

class Distances {
  private var _root: Cell = null;
  private var _cells = scala.collection.mutable.Map[Cell, Int]();

  def this(root: Cell) = {
    this();

    _root = root
    _cells += (root -> 0);
  }

  def get(cell: Cell): Int = {
    _cells.getOrElse(cell, Distances.NotFound)
  }

  def set(cell: Cell, distance: Int) = {
    _cells += (cell -> distance)
  }

  def cells: Iterable[Cell] = _cells.keys
}
