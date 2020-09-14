/**
  * The mighty Cell class
  */
package lib

import scala.collection.mutable.{ArrayBuffer, Map}

trait Cell {
  var row: Int
  var column: Int
}

trait MazeCell extends Cell {
  type T
  var links = Map[T, Boolean]()

  def getLinks(): Iterable[T] = links.keys
  def isLinked(cell: T): Boolean = links.contains(cell)
  def neighbors(): ArrayBuffer[T]

  // howowowowowowowowowow????????
  //def link(cell: MazeCell, bidi: Boolean = true): MazeCell
  //def unlink(cell: MazeCell, bidi: Boolean = true): MazeCell
  // TODO: link and unlink should be in the MazeCell trait
  def link(cell: T, bidi: Boolean = true): T = {
    links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    cell
  }

  def unlink(cell: T, bidi: Boolean = true): T = {
    links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    cell
  }
}

