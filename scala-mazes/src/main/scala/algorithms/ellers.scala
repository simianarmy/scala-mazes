package algorithms

import scala.collection.mutable.{ArrayBuffer, Map}

import lib.{Grid, MazeCell, Randomizer, RandomUtil}

class Ellers extends MazeGenerator with Randomizer {
  class RowState(size: Int, startingSet: Int = 0) {
    var cellsInSet = Map[Int, List[MazeCell]]()
    var setForCell = new Array[Int](size)
    var nextSet = startingSet

    def record(set: Int, cell: MazeCell): Unit =  {
      setForCell(cell.column) = set
      val currentCells = cellsInSet.getOrElse(set, List())
      cellsInSet(set) = cell :: currentCells
    }

    def setFor(cell: MazeCell): Int = {
      if (setForCell(cell.column) == 0) {
        record(nextSet, cell)
        nextSet += 1
      }
      setForCell(cell.column)
    }

    def merge(winner: Int, loser: Int): Unit = {
      for (cell <- cellsInSet(loser)) {
        setForCell(cell.column) = winner
        val currentCells = cellsInSet.getOrElse(winner, List())
        cellsInSet(winner) = cell :: currentCells
      }

      cellsInSet -= loser
    }

    def next: RowState = {
      new RowState(size, nextSet)
    }

    def eachSet(fn: (Int, List[MazeCell]) => Unit) = {
      for ((set, cells) <- cellsInSet) {
        fn(set, cells)
      }
    }
  }

  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    var rowState = new RowState(grid.columns)

    grid.eachRow(row => {
      val rowList = row.toList
      val head = rowList.head

      for (cell <- rowList) {
        if (!cell.west.isNil) {
          val set = rowState.setFor(cell)
          val priorSet = rowState.setFor(cell.west)
          val shouldLink = (set != priorSet) && (cell.south.isNil || rand.nextInt(2) == 0)

          if (shouldLink) {
            cell.linkBidirectional(cell.west)
            rowState.merge(priorSet, set)
          }
        }
      }

      // link to southern cells
      if (!head.south.isNil) {
        val nextRow = rowState.next

        rowState.eachSet((set, list) => {
          scala.util.Random.shuffle(list).zipWithIndex.foreach { case (cell, index) => {
            if (!cell.south.isNil && (index == 0 || rand.nextInt(3) == 0)) {
              cell.linkBidirectional(cell.south)
              nextRow.record(rowState.setFor(cell), cell.south)
            }
          }}
        })

        rowState = nextRow
      }
    })

    grid
  }

  override def toString = "Eller's"
}
