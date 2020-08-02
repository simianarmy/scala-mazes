import java.awt.Color

import lib.ColoredGrid

object Coloring extends MazeApp {
  var g = new ColoredGrid(rows, cols, Color.BLUE);

  g = generateMaze(g)

  val start = g.getCell(g.rows / 2, g.columns / 2)
  g.distances = start.distances

  printMaze(g)
}

