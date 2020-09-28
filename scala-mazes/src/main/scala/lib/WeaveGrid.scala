package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._

class WeaveGrid(override val rows: Int, override val columns: Int) extends OrthogonalGrid[OverCell](rows,columns) with Randomizer {
  protected var underCells = List[UnderCell]()

  override def id: String = "wv"

  override def eachCell(fn: (OverCell => Unit)): Unit = {
    super.eachCell(fn)
    underCells.foreach(fn)
  }

  def tunnelUnder(overCell: OverCell): Unit = {
    underCells = underCells ::: List(new UnderCell(overCell, this))
  }

  override def toPng(size: Int = 10, inset: Double = 0.1): BufferedImage = {
    super.toPng(size, inset)
  }
}
