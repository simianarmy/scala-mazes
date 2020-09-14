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
  var links = Map[MazeCell, Boolean]()

  def getLinks(): Iterable[MazeCell] = links.keys
  def isLinked(cell: MazeCell): Boolean = links.contains(cell)
  //def neighbors(): ArrayBuffer[MazeCell]
  //def link(cell: MazeCell, bidi: Boolean = true): MazeCell
  //def unlink(cell: MazeCell, bidi: Boolean = true): MazeCell
  // TODO: link and unlink should be in the MazeCell trait
  def link(cell: MazeCell, bidi: Boolean = true): MazeCell = {
    links += (cell -> true);

    if (bidi) {
      cell.link(this, false);
    }
    cell
  }

  def unlink(cell: MazeCell, bidi: Boolean = true): MazeCell = {
    links -= (cell);

    if (bidi) {
      cell.unlink(this, false);
    }
    cell
  }
}

