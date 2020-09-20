import lib.ColoredGrid

object Dijkstra extends MazeApp {
  var g = new ColoredGrid(rows, cols);

  val gg = generateMaze(g, alg)

  val start = gg.getCell(0, 0)
  var distances = start.distances
  gg.distances = distances

  println("Path from nw corner to sw corner")
  println(gg)

  gg.distances = distances.pathTo(gg.getCell(gg.rows - 1, 0))
  printMaze(gg)
}
