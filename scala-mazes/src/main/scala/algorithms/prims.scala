package algorithms

import lib.Grid
import lib.{RandomUtil, Randomizer}
import lib.MazeCell

class Prims extends GeneralGenerator {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {

    def process(active: List[A]): Unit = {
      if (active.isEmpty) {
        return
      }
      val cell = RandomUtil.sample(active)
      val availableNeighbors = cell.neighbors.filter(_.links.isEmpty)

      if (availableNeighbors.nonEmpty) {
        val neighbor = RandomUtil.sample(availableNeighbors)

        cell.linkBidirectional(neighbor)
        process(neighbor.asInstanceOf[A] :: active)
      } else {
        process(active diff List(cell)) // remove cell from active
      }
    }

    process(List[A](startCell.getOrElse(grid.randomCell())))
    grid
  }
}

class TruePrims extends GeneralGenerator with Randomizer {
  import scala.collection.mutable.PriorityQueue

  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A]): Grid[A] = {
    var costs = Map[A, Int]()
    def cellOrder(cell: A): Int = -costs(cell) // lower weight has higher priority
    var active = PriorityQueue[A](startCell.getOrElse(grid.randomCell()))(Ordering.by(cellOrder))

    def process(active: PriorityQueue[A]): Unit = {
      if (active.isEmpty) {
        return
      }
      val cell = active.dequeue()
      println("cell weight " + costs(cell))
      val availableNeighbors = cell.neighbors.filter(_.links.isEmpty)

      if (availableNeighbors.nonEmpty) {
        val neighbor = availableNeighbors.minBy[Int](cell => costs(cell.asInstanceOf[A]))

        cell.linkBidirectional(neighbor)
        active += neighbor.asInstanceOf[A]
        active += cell // re-enque
      }
      process(active)
    }

    grid.eachCell(cell => {
      costs = costs + (cell -> rand.nextInt(100))
    })

    process(active)
    grid
  }
}
