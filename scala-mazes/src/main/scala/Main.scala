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
  var g = new OrthogonalGrid[GridCell](rows, cols);
  val gg = generateMaze(g)

  printMaze(gg)
}
