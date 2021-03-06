package lib

import scala.reflect.{ClassTag, classTag}
import java.awt.Color

/**
  * Defines grid contract
  */
abstract class Grid[A <: MazeCell](val rows: Int, val columns: Int) extends TextRenderer with ImageRenderer with GridDistances with Randomizer {
  /**
    * Easier than extending Iterator[A] in the grid class itself
    *
    * @param grid
    */
  class GridIterator[A <: MazeCell](grid: Grid[A]) extends Iterator[A] {
    var position: Int = 0;
    def hasNext(): Boolean = {
      position < grid.size
    }
    def next(): A = {
      val el = grid.cellAt(position)
      position += 1
      el
    }
  }

  val dimensions = (rows, columns)
  def size: Int = rows * columns
  var _braid: Double = 0
  var color: Color = null
  def numCells: Int = size
  def getCell(row: Int, column: Int): A
  def cellAt(index: Int): A
  def randomCell(): A
  def id: String

  def iterator(): GridIterator[A] = {
    new GridIterator(this)
  }

  def eachRow(fn: (Iterator[A] => Unit)): Unit = {
    // collect cells into rows
    for (row <- iterator().grouped(columns)) {
      fn(row.iterator)
    }
  }

  def eachCell(fn: (A => Unit)): Unit = {
    iterator().filterNot(c => c.isNil).foreach(fn)
  }

  def setColor(col: Color) = {
    color = col
  }

  def deadends: List[A] = {
    iterator().filter(c =>  c.links.size == 1).toList
  }

  def braid(p: Double = 1.0) = {
    _braid = p
    val rand = new scala.util.Random(System.currentTimeMillis)

    scala.util.Random.shuffle(deadends)
      .filterNot(cell => {
        cell.links.size != 1 || rand.nextDouble() > p
      })
        .foreach(cell => {
          val neighbors = cell.neighbors.filter(n => {
            !cell.isLinked(n)
          })

          var best = neighbors.filter(n => {
            n.links.size == 1
          })

          if (best.isEmpty) {
            best = neighbors
          }

          if (!best.isEmpty) {
            val neighbor = RandomUtil.sample(best)
            cell.linkBidirectional(neighbor)
          }
        })
  }
}

