import lib.PolarGrid

object PolarGridApp extends MazeApp {
  var g = new PolarGrid(rows);

  printMaze(generateMaze(g))
}
