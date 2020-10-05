import java.awt.Color

import lib.{OrthogonalGrid, Colored, RainbowColored, GridCell}

object AldousBroderColored extends MazeApp {
  for (i <- 1 to 6) {
    var g = if (conf.rainbow) new OrthogonalGrid[GridCell](20, 20) with RainbowColored[GridCell]
    else new OrthogonalGrid[GridCell](20, 20) with Colored[GridCell]
    val gg = generateMaze(g, "ab")

    gg.distances = gg.getCell(gg.rows / 2, gg.columns / 2).distances

    val filename = "generated/maze-ab-" + i + ".png"
    MazeApp.gridToPng(gg, filename)
  }
}

