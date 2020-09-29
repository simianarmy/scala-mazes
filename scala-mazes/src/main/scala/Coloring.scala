import java.awt.Color

import lib.ColoredGrid

object Coloring extends MazeApp {
  var g = new ColoredGrid(rows, cols, Color.BLUE);

  val gg = generateMaze(g, alg)

  gg.distances = gg.getCell(g.rows / 2, g.columns / 2) match {
    case Some(cell) => cell.distances
    case _ => null
  }

  printMaze(gg)
}

