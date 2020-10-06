package lib

import scala.collection.mutable.PriorityQueue

class WeightedCell(row: Int, column: Int) extends GridCell(row, column) {
  weight = 1

  override def distances: Distances[MazeCell] = {
    val weights = new Distances[MazeCell](this)
    def cellOrder(cell: MazeCell): Int = -weights.get(cell) // lower weight has higher priority
    var pending = PriorityQueue[MazeCell](this)(Ordering.by(cellOrder))

    while (pending.nonEmpty) {
      println("pending " + pending)
      val cell = pending.dequeue()

      for (neighbor <- cell.links) {
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
