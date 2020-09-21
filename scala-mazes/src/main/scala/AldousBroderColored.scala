import java.awt.Color

import lib.ColoredGrid

object AldousBroderColored extends MazeApp {
  for (i <- 1 to 6) {
    var g = new ColoredGrid(20, 20);
    val gg = generateMaze(g, "ab")

    val middle = gg.getCell(gg.rows / 2, gg.columns / 2)
    gg.distances = middle.distances

    val filename = "generated/maze-ab-" + i + ".png"
    MazeApp.gridToPng(gg, filename)
  }
}

