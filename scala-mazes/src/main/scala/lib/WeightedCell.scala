package lib

import scala.collection.mutable.ListBuffer

class WeightedCell(row: Int, column: Int) extends GridCell(row, column) {
  weight = 1

  override def distances(): Distances[MazeCell] = {
    val weights = new Distances[MazeCell](this)
    var pending = ListBuffer[MazeCell](this)

    while (pending.size > 0) {
      val cell = pending.sorted.head
      pending -= cell

      for (neighbor <- cell.getLinks()) {
        val totalWeight = weights.get(cell) + neighbor.weight

        if (weights.get(neighbor) == Distances.NotFound || totalWeight < weights.get(neighbor)) {
          pending += neighbor
          weights.set(neighbor, totalWeight)
        }
      }
    }

    weights
  }

  override def toString: String =
    s"[WeightedCell: $row, $column: $weight]"
}
