package lib

import scala.reflect.{ClassTag, classTag}

object GridCell {
  def createCell[A <: AnyRef : ClassTag](row: Int, column: Int): A = {
    val constructor = classTag[A].runtimeClass.getConstructors.head
    //println("constructing " + constructor + s" with $row, $column")
    constructor.newInstance(row, column).asInstanceOf[A]
  }

  def nilCell[A <: AnyRef : ClassTag]: A = {
    createCell[A](-1, -1)
  }

  def cellOrNil[A](cell: Option[A]): A = cell match {
    case Some(cell) => cell
    case _ => nilCell
  }
}

class GridCell(row: Int, column: Int) extends MazeCell(row, column) {
  var north: GridCell = null
  var south: GridCell = null
  var east: GridCell = null
  var west: GridCell = null

  def neighbors: List[GridCell] = {
    for (dir <- List(north, south, east, west) if dir != null) yield dir
  }

  override def toString: String =
    s"[GridCell: " + row + ", " + column + "]";
}
