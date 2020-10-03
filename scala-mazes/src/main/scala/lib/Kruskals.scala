package lib

//import scala.collection.mutable.ListBuffer

class Kruskals {
  class State[A <: GridCell](grid: Grid[A]) {
    var neighbors: List[(A, A)] = List()
    private var setForCell = Map[A, Int]()
    private var cellsInSet = Map[Int, List[A]]()

    grid.eachCell(cell => {
      val set = setForCell.size

      setForCell = setForCell + (cell -> set)
      cellsInSet = cellsInSet + (set -> List(cell))

      if (!cell.south.isNil) {
        neighbors = neighbors :+ ((cell, cell.south.asInstanceOf[A]))
      }
      if (!cell.east.isNil) {
        neighbors = neighbors :+ ((cell, cell.east.asInstanceOf[A]))
      }
    })

    def canMerge(left: A, right: A): Boolean = setForCell(left) != setForCell(right)

    def merge(left: A, right: A): Unit = {
      left.linkBidirectional(right)

      val winner = setForCell(left)
      val loser = setForCell(right)
      val losers = if (cellsInSet(loser).size > 0) cellsInSet(loser) else List(right)

      for (cell <- losers) {
        cellsInSet = cellsInSet + (winner -> (cellsInSet(winner) :+ cell))
        setForCell = setForCell + (cell -> winner)
        cellsInSet = cellsInSet - loser
      }
    }
  }

  def on[A <: GridCell](grid: Grid[A]): Grid[A] = {
    val state = new State[A](grid)

    def mergeNext(neighbors: List[(A, A)]): Unit = {
      neighbors match {
        case next :: rest => {
          val (left, right) = next

          if (state.canMerge(left, right)) {
            state.merge(left, right)
          }
          mergeNext(rest) // recurse
        }
        case Nil => ()
      }
    }

    val neighbors = scala.util.Random.shuffle(state.neighbors)
    mergeNext(neighbors)

    grid
  }
}

