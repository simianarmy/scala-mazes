package algorithms

import scala.collection.mutable.ArrayBuffer

import lib.{Grid, Cell3D, MazeCell, RandomUtil, Randomizer}

class Sidewinder extends MazeGenerator with Randomizer {
  def on[A <: MazeCell](grid: Grid[A], startCell: Option[A])(op: List[A] => A): Grid[A] = {
    var buffer = new ArrayBuffer[MazeCell]()

    grid.eachRow(itr => {
      buffer.clear()

      while (itr.hasNext) {
        val cell = itr.next()
        buffer += cell

        val east = grid.getCell(cell.row, cell.column + 1)
        val atEasternBoundary = east.isNil
        val atNorthernBoundary = cell.north.isNil
        val shouldCloseOut =
          atEasternBoundary || (!atNorthernBoundary && rand.nextInt(2) == 0)

        if (shouldCloseOut) {
          var member = buffer(rand.nextInt(buffer.length))

          // pick either member's north or up
          val other = member match {
            case c: Cell3D => List(c.up)
            case _ => Nil
          }
          val choices = (List(member.north) ::: other).filterNot(_.isNil)

          if (choices.nonEmpty) {
            member.linkBidirectional(choices(0));
          }
          buffer.clear();
        } else {
          cell.linkBidirectional(east);
        }
      }
    })
    grid
  }

  override def toString: String = "Sidewinder"
}
