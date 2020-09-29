package lib

import scala.collection.mutable.{ArrayBuffer, Map}
import scala.reflect.{ClassTag, classTag}

abstract class Cell(val row: Int, val column: Int)

object MazeCell {
  def createCell[A <: AnyRef : ClassTag](row: Int, column: Int): A = {
    val constructor = classTag[A].runtimeClass.getConstructors.head
    //println("constructing " + constructor + s" with $row, $column")
    constructor.newInstance(row, column).asInstanceOf[A]
  }

  def nilCell[A <: AnyRef : ClassTag]: A = {
    createCell[A](-1, -1)
  }

  def cellOrNil[A <: AnyRef : ClassTag](cell: Option[A]): A = cell match {
    case Some(cell) => cell
    case _ => nilCell[A]
  }
}

abstract class MazeCell(row: Int, column: Int) extends Cell(row, column) with Ordered[MazeCell] {
  var weight: Int = 0
  // TODO: Why not use a Set ?
  var links = Map[MazeCell, Boolean]()

  def getLinks(): Iterable[MazeCell] = links.keys
  def isLinked(cell: MazeCell): Boolean = links.contains(cell)
  def isNil = row == -1 && column == -1
  def neighbors: List[MazeCell]
  def distances(): Distances[MazeCell] = {
    new CellDistanceFinder().distances(this)
  }
  def compare(that: MazeCell) = weight compare that.weight

  def link[A <: MazeCell](cell: A): Unit = {
    links += (cell -> true);
  }

  def linkBidirectional[A <: MazeCell](cell: A): Unit = {
    link(cell)
    cell.link(this)
  }

  def unlink(cell: MazeCell): Unit = {
    links -= (cell);
  }

  def unlinkBidirectional(cell: MazeCell): Unit = {
    unlink(cell)
    cell.unlink(this)
  }
}

