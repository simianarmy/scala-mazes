import lib.Grid

import algorithms._

class MazeApp extends App {
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";
  var ascii = args.length > 3 && args(3) == "txt"

  def generateMaze[T <: Grid](g: T): T = {
    alg match {
      case "sw" => Sidewinder.on(g)
      case "ab" => AldousBroder.on(g)
      case "wi" => Wilsons.on(g)
      case "hk" => HuntKill.on(g)
      case "rb" => RecursiveBacktracker.on(g)
      case "bt"  => BinaryTree.on(g)
      case _ => g
    }
  }

  def printMaze(g: Grid): Unit = {
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
}
