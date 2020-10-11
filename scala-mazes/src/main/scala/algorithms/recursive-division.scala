package algorithms

import lib.{Grid, MazeCell, Randomizer}

class RecursiveDivision extends MazeGenerator with Randomizer {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    def divide(row: Int, column: Int, height: Int, width: Int): Unit = {
      if (height <= 1 || width <= 1 || height < 5 && width < 5 && rand.nextInt(4) == 0) {
        return
      }
      if (height > width)
        divideHorizontally(row, column, height, width)
      else
        divideVertically(row, column, height, width)
    }

    def divideHorizontally(row: Int, column: Int, height: Int, width: Int) = {
      val divideSouthOf = rand.nextInt(height - 1)
      val passageAt = rand.nextInt(width)

      for (i <- 0 until width if i != passageAt) {
        val cell = grid.getCell(row + divideSouthOf, column + i)
        cell.unlinkBidirectional(cell.south)
      }

      divide(row, column, divideSouthOf + 1, width)
      divide(row + divideSouthOf + 1, column, height - divideSouthOf - 1, width)
    }

    def divideVertically(row: Int, column: Int, height: Int, width: Int) = {
      val divideEastOf = rand.nextInt(width - 1)
      val passageAt = rand.nextInt(height)

      for (i <- 0 until height if i != passageAt) {
        val cell = grid.getCell(row + i, column + divideEastOf)
        cell.unlinkBidirectional(cell.east)
      }

      divide(row, column, height, divideEastOf + 1)
      divide(row, column + divideEastOf + 1, height, width - divideEastOf - 1)
    }

    grid.eachCell(cell => {
      cell.neighbors.foreach(cell.link(_))
    })

    divide(0, 0, grid.rows, grid.columns)
    grid
  }

  override def toString: String = "Recursive Division"
}
