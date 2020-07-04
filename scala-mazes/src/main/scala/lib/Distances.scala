/**
  * @class Distances class
  * Used for Dijkstra's algorithm
  */

import scala.util.control.Breaks._

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

  /**
    * Calculate shortest path from _root to goal
    */
  def pathTo(goal: Cell): Distances = {
    var current = goal
    var breadcrumbs = new Distances(_root)

    breadcrumbs.set(current, this.get(current))

    while (current != _root) {
      var links = current.getLinks()

      breakable {
        for (neighbor <- links) {
          if (this.get(neighbor) < this.get(current)) {
            breadcrumbs.set(neighbor, this.get(neighbor))
            current = neighbor
            break
          }
        }
      }
    }

    breadcrumbs
  }

  /**
    * Calculates furthest cell from _root
    * @return (furthest cell, distance)
    */
  def max(): (Cell, Int) = {
    var max = (_root, 0)

    for ((cell, distance) <- _cells) {
      if (distance > max._2) {
        max = (cell, distance)
      }
    }
    max
  }
}
