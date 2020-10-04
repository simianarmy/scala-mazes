import java.awt.Color

import lib.ColoredGrid

object Coloring extends MazeApp {
  var g = new ColoredGrid(rows, cols, Color.BLUE);

  val gg = generateMaze(g)

  gg.distances = gg.getCell(g.rows / 2, g.columns / 2).distances

  printMaze(gg)
}

