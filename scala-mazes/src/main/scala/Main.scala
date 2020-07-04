/**
  * Arguments:
  *  rows
  *  columns
  *  algorithm id (bt|sw|)
  *  ascii flag (txt)
  *
  * ex: run 5 5
  * ex: run 10 10 sw
  * ex: run 10 10 bt txt
  */
object Main extends App {
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";
  var ascii = args.length > 3 && args(3) == "txt"
  var g = new Grid(rows, cols);

  g = alg match {
    case "sw" => Sidewinder.on(g)
    case _    => BinaryTree.on(g)
  }
  if (ascii) {
    println(g);
  } else {
    // draw image to a file
    val filename = "generated/maze-" + alg + "-" + rows + "x" + cols + ".png"
    javax.imageio.ImageIO.write(
      g.toPng(),
      "png",
      new java.io.File(filename)
    )
    println("image saved to " + filename)
  }
}
