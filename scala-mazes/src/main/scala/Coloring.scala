import java.awt.Color

//import lib.{ColoredGrid, RainbowColoredGrid}
import lib.{OrthogonalGrid, Colored, RainbowColored, GridCell}

object Coloring extends MazeApp {
  var g = if (conf.rainbow) new OrthogonalGrid[GridCell](rows, cols) with RainbowColored[GridCell]
  else  new OrthogonalGrid[GridCell](rows, cols) with Colored[GridCell]

  val gg = generateMaze(g)

  //gg.distances = gg.getCell(g.rows / 2, g.columns / 2).distances

  printMaze(gg)
}

