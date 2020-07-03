object Main extends App {
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";
  var g = new Grid(rows, cols);

  g = alg match {
    case "bt" => BinaryTree.on(g)
    case "sw" => Sidewinder.on(g)
    case _    => BinaryTree.on(g)
  }
  //println(g);
  // draw image to a file
  val filename = "maze-" + alg + "-" + rows + "x" + cols + ".png"
  javax.imageio.ImageIO.write(
    g.toPng(),
    "png",
    new java.io.File("generated/" + filename)
  )
}
