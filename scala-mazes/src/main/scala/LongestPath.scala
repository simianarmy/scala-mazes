import lib.DistanceGrid


object LongestPath extends MazeApp {
  var g = new DistanceGrid(rows, cols);

  val gg = generateMaze(g, alg)
  val distances = gg.getCell(0, 0) match {
    case Some(cell) => cell.distances
    case _ => null
  }

  val (newStart, _) = distances.max()
  val newDistances = newStart.distances
  val (goal, _) = newDistances.max()

  gg.distances = newDistances.pathTo(goal)

  printMaze(gg, toAscii = true)
}
