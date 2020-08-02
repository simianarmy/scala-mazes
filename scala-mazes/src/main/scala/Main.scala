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
 import lib.Grid

object Main extends MazeApp {
  var g = new Grid(rows, cols);

  g = generateMaze(g)

  printMaze(g)
}
