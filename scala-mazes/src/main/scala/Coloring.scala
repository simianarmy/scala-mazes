import java.awt.Color

import lib.{ColoredGrid, RainbowColoredGrid}

object Coloring extends MazeApp {
  var g = if (conf.rainbow) new RainbowColoredGrid(rows, cols, Color.BLUE)
  else  new ColoredGrid(rows, cols, Color.BLUE)

  val gg = generateMaze(g)

  gg.distances = gg.getCell(g.rows / 2, g.columns / 2).distances

  printMaze(gg)
}

