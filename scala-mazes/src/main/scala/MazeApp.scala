import lib._
import algorithms._

object MazeApp {
  def gridToPng[A <: MazeCell](grid: Grid[A], filename: String, inset: Double = 0) = {
    javax.imageio.ImageIO.write(
      grid.toPng(inset = inset),
      "png",
      new java.io.File(filename)
    )
    println("image saved to " + filename)
  }
}

class MazeApp extends App {
  /**
    * parse commom args
    */
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";
  val braidValue: Double = if (args.length > 3) args(3).toDouble else 1.0

  def generateMaze[A <: MazeCell](grid: Grid[A], algorithm: String = alg): Grid[A] = {
    val gen = algorithm match {
      case "sw" => new Sidewinder()
      case "ab" => new AldousBroder()
      case "wi" => new Wilsons()
      case "hk" => new HuntKill()
      case "rb" => new RecursiveBacktracker()
      case "bt" => new BinaryTree()
      case _ => new Wilsons()
    }
    //println("Generating maze with "+ gen)
    gen.on(grid, None)
  }

  def printMaze[A <: MazeCell](g: Grid[A], toAscii: Boolean = false, inset: Double = 0): Unit = {
    if (toAscii) {
      println(g);
    } else {
      // draw image to a file
      val filename = "generated/maze-" + g.id + "-" + alg + "-" + g.rows + "x" + g.columns + ".png"
      MazeApp.gridToPng(g, filename, inset)
    }
  }
}
