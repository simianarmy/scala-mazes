import lib.DistanceGrid


object LongestPath extends MazeApp {
  var g = new DistanceGrid(rows, cols);

  val gg = generateMaze(g, alg)

  val start = gg.getCell(0, 0)
  val distances = start.distances

  val (newStart, _) = distances.max()
  val newDistances = newStart.distances
  val (goal, _) = newDistances.max()

  gg.distances = newDistances.pathTo(goal)

  printMaze(gg, toAscii = true)
}
