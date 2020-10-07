/**
  * Arguments:
  *  rows
  *  columns
  *  algorithm id (bt|sw|)
  *  ascii flag (txt)
  *
  * ex: run 5 5
  * ex: run 10 10 sw
  * ex: run 10 10 bt txt
  */
 import lib._

object Main extends MazeApp {
  // somehow this work but making this a function doesnt ??
  val g = conf.shape match {
    case "triangle" => makeTriangleGrid
    case "hex" => makeHexGrid
    // TODO: Fix this
    //case "polar" => new PolarGrid(rows)
    case "weave" => makeWeaveGrid
    case _ => makeGrid
  }

  val gg = generateMaze(g)

  printMaze(gg)
}
