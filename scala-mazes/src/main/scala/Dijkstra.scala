import lib.{DistanceGrid, Distances, MazeCell, GridCell}

import lib.MazeCell._

object Dijkstra extends MazeApp {
  var g = new DistanceGrid(rows, cols);
  val gg = generateMaze(g)
  val distances = gg.getCell(0, 0) match {
    case Some(cell) => cell.distances
    case _ => null
  }

  gg.distances = distances

  println("Path from nw corner to sw corner")
  printMaze(gg, toAscii = true)

  gg.distances = distances.pathTo(cellOrNil(gg.getCell(gg.rows - 1, 0)))
  printMaze(gg, toAscii = true)
}
