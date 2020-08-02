import lib.DistanceGrid


object LongestPath extends MazeApp {
  var g = new DistanceGrid(rows, cols);

  g = generateMaze(g)

  val start = g.getCell(0, 0)
  val distances = start.distances

  val (newStart, _) = distances.max()
  val newDistances = newStart.distances
  val (goal, _) = newDistances.max()

  g.distances = newDistances.pathTo(goal)

  printMaze(g)
}
