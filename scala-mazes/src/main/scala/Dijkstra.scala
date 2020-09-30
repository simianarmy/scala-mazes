import lib.{DistanceGrid, Distances, MazeCell, GridCell}

import lib.MazeCell._

object Dijkstra extends MazeApp {
  var g = new DistanceGrid(rows, cols);
  val gg = generateMaze(g)
  val distances = gg.getCell(0, 0).distances

  gg.distances = distances
  println("distances")
  printMaze(gg, toAscii = true)

  gg.distances = distances.pathTo(gg.getCell(gg.rows - 1, 0))
  println("Path from nw corner to sw corner")
  printMaze(gg, toAscii = true)
}
