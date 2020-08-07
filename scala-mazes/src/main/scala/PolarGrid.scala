 import lib.PolarGrid

object PolarGrid extends MazeApp {
  var g = new PolarGrid(rows, cols);

  g = generateMaze(g)

  printMaze(g)
}
