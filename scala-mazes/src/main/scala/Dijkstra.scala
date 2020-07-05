object Dijkstra extends App {
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";

  var g = new ColoredGrid(rows, cols);

  g = alg match {
    case "sw" => Sidewinder.on(g)
    case _    => BinaryTree.on(g)
  }
  val start = g.getCell(0, 0)
  var distances = start.distances
  g.distances = distances

  println("Path from nw corner to sw corner")
  println(g)
  g.distances = distances.pathTo(g.getCell(g.rows - 1, 0))

  val filename = "generated/maze-" + alg + "-" + rows + "x" + cols + ".png"
  PngWriter.saveGrid(g, filename)
}
