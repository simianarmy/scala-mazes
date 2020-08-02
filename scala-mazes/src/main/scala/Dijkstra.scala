import lib.ColoredGrid

object Dijkstra extends MazeApp {
  var g = new ColoredGrid(rows, cols);

  g = generateMaze(g)

  val start = g.getCell(0, 0)
  var distances = start.distances
  g.distances = distances

  println("Path from nw corner to sw corner")
  println(g)

  g.distances = distances.pathTo(g.getCell(g.rows - 1, 0))
  printMaze(g)
}
