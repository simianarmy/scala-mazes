/**
  * @class Distances class
  *
  * Uses Dijkstra's algorithm to calculate distances between maze cells
  */
package lib

import scala.util.control.Breaks._

object Distances {
  val NotFound = -1
}

class Distances[C](root: C) {
  var _cells = scala.collection.mutable.Map[C, Int](root -> 0)

  def get(cell: C): Int = {
    _cells.getOrElse(cell, Distances.NotFound)
  }

  def set(cell: C, distance: Int) = {
    _cells += (cell -> distance)
  }

  def cells: Iterable[C] = _cells.keys

  /**
    * Calculate shortest path from _root to goal
    */
  def pathTo(goal: C): Distances[C] = {
    var current: C = goal
    var breadcrumbs = new Distances[C](root)

    breadcrumbs.set(current, this.get(current))

    while (current != root) {
      var links = current.asInstanceOf[MazeCell].getLinks()

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
  def max(): (C, Int) = {
    var max = (root, 0)

    for ((cell, distance) <- _cells) {
      if (distance > max._2) {
        max = (cell, distance)
      }
    }
    max
  }
}
