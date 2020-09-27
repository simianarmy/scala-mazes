package lib

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color, Font, BasicStroke, Polygon, RenderingHints}
import java.awt.geom._

case class WeaveGrid(override val rows: Int, override val columns: Int) extends Grid[OverCell](rows,columns) with Randomizer {
  protected val grid = prepareGrid()
  protected var underCells = List[UnderCell]()

  protected def prepareGrid(): Array[Array[OverCell]] = {
    var cells = Array.ofDim[OverCell](rows, columns);

    for (i <- 0 until rows; j <- 0 until columns) {
      cells(i)(j) = new OverCell(i, j, this);
    }

    cells
  }

  override def id: String = "wv"

  // Direct (unsafe) element accessor
  def apply(row: Int): Array[OverCell] = grid(row)

  override def eachCell(fn: (OverCell => Unit)): Unit = {
    super.eachCell(fn)
    underCells.foreach(fn)
  }

  // Safe element accessor
  def getCell(row: Int, column: Int): OverCell = {
    if (row < 0 || row >= rows || column < 0 || column >= columns) null
    else grid(row)(column)
  }

  def cellAt(index: Int): OverCell = {
    val x = index / columns
    val y = index % columns

    getCell(x, y)
  }

  def randomCell(): OverCell = {
    val row = rand.nextInt(rows);
    val column = rand.nextInt(grid(row).length);

    getCell(row, column)
  }

  def tunnelUnder(overCell: OverCell): Unit = {
    underCells = underCells ::: List(new UnderCell(overCell, this))
  }

  override def toPng(size: Int = 10, inset: Double = 0.1): BufferedImage = {
    super.toPng(size, inset)
  }
}
