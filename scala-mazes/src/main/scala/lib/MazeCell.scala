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

  def createCell[A <: AnyRef : ClassTag](row: Int, column: Int, grid: WeaveGrid): A = {
    val constructor = classTag[A].runtimeClass.getConstructors.head
    //println("createCell2 constructing " + constructor + s" with $row, $column")
    constructor.newInstance(row, column, grid).asInstanceOf[A]
  }

  def nilCell[A <: AnyRef : ClassTag]: A = {
    createCell[A](-1, -1)
  }

  def cellOrNil[A <: AnyRef : ClassTag](cell: Option[A]): A = cell.getOrElse(nilCell[A])
}

abstract class MazeCell(row: Int, column: Int) extends Cell(row, column) with CellDistances {
  // TODO: Why not use a Set ?
  var _links = Map[MazeCell, Boolean]()

  def links: Iterable[MazeCell] = _links.keys
  def isLinked(cell: MazeCell): Boolean = _links.contains(cell)
  def isNil = row == -1 && column == -1
  def neighbors: List[MazeCell]

  def link[A <: MazeCell](cell: A): Unit = {
    _links += (cell -> true);
  }

  def linkBidirectional[A <: MazeCell](cell: A): Unit = {
    link(cell)
    cell.link(this)
  }

  def unlink(cell: MazeCell): Unit = {
    _links -= (cell);
  }

  def unlinkBidirectional(cell: MazeCell): Unit = {
    unlink(cell)
    cell.unlink(this)
  }

  // TODO: These used to be in GridCell, but caused insane headaches trying to use in maze algorithms
  // with non-grid cells.  Adding these to the base class until I can figure out a way around it
  protected var _north: Option[MazeCell] = None
  protected var _south: Option[MazeCell] = None
  protected var _east: Option[MazeCell] = None
  protected var _west: Option[MazeCell] = None

  def north: MazeCell = _north.getOrElse(MazeCell.nilCell[MazeCell])
  def south: MazeCell = _south.getOrElse(MazeCell.nilCell[MazeCell])
  def east: MazeCell = _east.getOrElse(MazeCell.nilCell[MazeCell])
  def west: MazeCell = _west.getOrElse(MazeCell.nilCell[MazeCell])

  def north_=(that: MazeCell) = _north = Some(that)
  def south_=(that: MazeCell) = _south = Some(that)
  def east_=(that: MazeCell) = _east = Some(that)
  def west_=(that: MazeCell) = _west = Some(that)
}

