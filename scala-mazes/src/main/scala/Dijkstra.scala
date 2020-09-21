import lib.{DistanceGrid, Distances, MazeCell, GridCell}

object Dijkstra extends MazeApp {
  var g = new DistanceGrid(rows, cols);
  val gg = generateMaze(g)

  val start = gg.getCell(0, 0)
  val distances = start.distances

  gg.distances = distances

  println("Path from nw corner to sw corner")
  printMaze(gg, toAscii = true)

  gg.distances = distances.pathTo(gg.getCell(gg.rows - 1, 0))
  printMaze(gg, toAscii = true)
}
