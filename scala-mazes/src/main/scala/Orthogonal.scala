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

object Orthogonal extends MazeApp {
  val g = conf.shape match {
    case "triangle" => makeTriangleGrid
    case "hex" => makeHexGrid
    case "weave" => makeWeaveGrid
    // TODO: would be great to be able to include Polar here
    case _ => makeGrid
  }

  val gg = generateMaze(g)

  printMaze(gg)
}
