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

class Distances[C <: MazeCell](root: C) {
  var _cells = scala.collection.mutable.Map[C, Int](root -> 0)

  def get(cell: C): Int = {
    _cells.getOrElse(cell, Distances.NotFound)
  }

  def set(cell: C, distance: Int) = {
    _cells += (cell -> distance)
  }

  def cells: Iterable[C] = _cells.keys

  def size: Int = cells.size

  /**
    * Calculate shortest path from _root to goal
    */
  def pathTo(goal: C): Distances[C] = {
    var current: C = goal
    var breadcrumbs = new Distances[C](root)

    breadcrumbs.set(current, this.get(current))

    while (current != root) {
      val links = current.links

      breakable {
        for (neighbor <- links) {
          // cast neighbor to C I guess
          val cneighbor = neighbor.asInstanceOf[C]

          if (this.get(cneighbor) < this.get(current)) {
            breadcrumbs.set(cneighbor, this.get(cneighbor))
            current = cneighbor
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

  override def toString: String = _cells.toSeq.sortBy(_._2) mkString("\n")
}
