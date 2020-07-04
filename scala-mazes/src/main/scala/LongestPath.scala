object LongestPath extends App {
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";

  var g = new DistanceGrid(rows, cols);

  g = alg match {
    case "sw" => Sidewinder.on(g)
    case _    => BinaryTree.on(g)
  }
  val start = g.getCell(0, 0)
  val distances = start.distances

  val (newStart, _) = distances.max()
  val newDistances = newStart.distances
  val (goal, _) = newDistances.max()

  g.distances = newDistances.pathTo(goal)

  println(g);
}
