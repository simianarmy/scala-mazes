/**
  * The mighty Cell class
  */
package lib

import scala.collection.mutable.{ArrayBuffer, Map}

trait Cell {
  var row: Int
  var column: Int
  def toString: String =
    s"[Cell: " + row + ", " + column + "]"
}

trait MazeCell extends Cell {
  var links = Map[MazeCell, Boolean]()

  def getLinks(): Iterable[MazeCell] = links.keys
  def isLinked(cell: MazeCell): Boolean = links.contains(cell)
  def neighbors(): ArrayBuffer[MazeCell]
  def link(cell: MazeCell, bidi: Boolean = true): MazeCell
  def unlink(cell: MazeCell, bidi: Boolean = true): MazeCell
}

