package algorithms

import lib.{Grid, MazeCell, RandomUtil, Randomizer}

class GrowingTree extends GeneralGenerator with Randomizer {
  override def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {

    def process(active: List[A]): Unit = {
      if (active.isEmpty) {
        return
      }
      val cell = op(active)
      val availableNeighbors = cell.neighbors.filter(_.links.isEmpty)

      if (availableNeighbors.nonEmpty) {
        val neighbor = RandomUtil.sample(availableNeighbors)

        cell.linkBidirectional(neighbor)
        process(active :+ neighbor.asInstanceOf[A])
      } else {
        process(active diff List(cell)) // remove cell from active
      }
    }

    process(List[A](startCell.getOrElse(grid.randomCell())))
    grid
  }

  override def toString: String = "Growing Tree"
}
