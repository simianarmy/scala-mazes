import java.awt.Color

import lib.RainbowColoredGrid

object RainbowColoring extends MazeApp {
  var g = new RainbowColoredGrid(rows, cols, Color.BLUE);

  val gg = generateMaze(g)

  gg.distances = gg.getCell(g.rows / 2, g.columns / 2).distances

  printMaze(gg)
}

