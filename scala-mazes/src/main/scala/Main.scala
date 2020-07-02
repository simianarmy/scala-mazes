//import Grid
//import algorithms._

object Main extends App {
  val rows : Int = if (args.length > 1) args(0).toInt else 4;
  val cols : Int = if (args.length > 1) args(1).toInt else 4;
  val alg : String = if (args.length > 2) args(2) else "bt";
  var g = new Grid(rows, cols);

  g = alg match {
    case "bt" => BinaryTree.on(g)
    case "sw" => Sidewinder.on(g)
    case _ => BinaryTree.on(g)
  }
  println(g);
}

