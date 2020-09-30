package lib

import scala.collection.mutable.ArrayBuffer

import lib.MazeCell._

class PolarCell(row: Int, column: Int) extends MazeCell(row, column) {
  private var _cw: Option[PolarCell] = None
  private var _ccw: Option[PolarCell] = None
  private var _inward: Option[PolarCell] = None
  private var _outward = new ArrayBuffer[PolarCell]()

  def cw = _cw.getOrElse(nilCell[PolarCell])
  def ccw = _ccw.getOrElse(nilCell[PolarCell])
  def inward = _inward.getOrElse(nilCell[PolarCell])
  def outward = _outward

  def cw_=(that: PolarCell) = _cw = Some(that)
  def ccw_=(that: PolarCell) = _ccw = Some(that)
  def inward_=(that: PolarCell) = _inward = Some(that)

  def neighbors: List[MazeCell] = {
    List(cw, ccw, inward).filterNot(p => p.isNil) ++ outward
  }

  override def toString: String =
    s"[PolarCell: " + row + ", " + column + "]";
}
