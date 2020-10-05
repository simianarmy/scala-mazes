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
    case "square" => new OrthogonalGrid[GridCell](rows, cols)
    case "triangle" => new TriangleGrid(rows, cols)
    case "hex" => new HexGrid(rows, cols)
    // TODO: Fix this
    //case "polar" => new PolarGrid(rows)
    case "weave" => new WeaveGrid(rows, cols)
  }

  val gg = generateMaze(g)

  printMaze(gg)
}
