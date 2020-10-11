package lib

//import scala.collection.mutable.ListBuffer

class Kruskals {
  class State(grid: WeaveGrid) extends Randomizer {
    var neighbors: List[(MazeCell, MazeCell)] = List()
    private var setForCell = Map[MazeCell, Int]().withDefaultValue(0)
    private var cellsInSet = Map[Int, List[MazeCell]]().withDefaultValue(List())

    grid.eachCell(cell => {
      val set = setForCell.size

      setForCell = setForCell + (cell -> set)
      cellsInSet = cellsInSet + (set -> List(cell))

      if (!cell.south.isNil) {
        neighbors = neighbors :+ ((cell, cell.south))
      }
      if (!cell.east.isNil) {
        neighbors = neighbors :+ ((cell, cell.east))
      }
    })

    def canMerge(left: MazeCell, right: MazeCell): Boolean = setForCell(left) != setForCell(right)

    def merge(left: MazeCell, right: MazeCell): Unit = {
      left.linkBidirectional(right)

      val winner = setForCell(left)
      val loser = setForCell(right)
      val losers =  cellsInSet(loser) match {
        case l: List[MazeCell] if l.size > 0 => l
        case _ => List(right)
      }

      for (cell <- losers) {
        cellsInSet = cellsInSet + (winner -> (cellsInSet(winner) :+ cell))
        setForCell = setForCell + (cell -> winner)
        cellsInSet = cellsInSet - loser
      }
    }

    def addCrossing(cell: MazeCell): Boolean = {
      if (cell.links.nonEmpty || !canMerge(cell.east, cell.west) || !canMerge(cell.north, cell.south)) {
        return false
      }

      neighbors = neighbors.filterNot(n => {
        val (left, right) = n
        left == cell || right == cell
      })

      if (rand.nextInt(2) == 0) {
        merge(cell.west, cell)
        merge(cell, cell.east)

        cell match {
          case c: OverCell => grid.tunnelUnder(c)
          case _ => ()
        }
        merge(cell.north, cell.north.south)
        merge(cell.south, cell.south.north)
      } else {
        merge(cell.north, cell)
        merge(cell, cell.south)

        cell match {
          case c: OverCell => grid.tunnelUnder(c)
          case _ => ()
        }
        merge(cell.west, cell.west.east)
        merge(cell.east, cell.east.west)
      }
      true
    }
  }

  def newState(grid: WeaveGrid) = new State(grid)

  def on(grid: WeaveGrid, state: State): WeaveGrid = {

    def mergeNext(neighbors: List[(MazeCell, MazeCell)]): Unit = {
      neighbors match {
        case (left, right) :: rest => {
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

