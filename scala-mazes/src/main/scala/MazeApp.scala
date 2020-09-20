import lib._
import algorithms._

class MazeApp extends App {
  val rows = if (args.length > 1) args(0).toInt else 4;
  val cols = if (args.length > 1) args(1).toInt else 4;
  val alg = if (args.length > 2) args(2) else "bt";
  var ascii = args.length > 3 && args(3) == "txt"

  def generateMaze(cg: ColoredGrid, algorithm: String): ColoredGrid = {
    cg
  }
  def generateMaze(og: OrthogonalGrid, algorithm: String): OrthogonalGrid = {
    og
  }
  def generateMaze(og: OrthogonalGrid, algorithm: String, startCell: MazeCell): OrthogonalGrid = {
    og
  }
  def generateMaze(dg: DistanceGrid, algorithm: String): DistanceGrid = {
    dg
  }

  def generateMaze[A <: MazeCell](grid: Grid[A], algorithm: String = alg): Grid[A] = {
    val gen = alg match {
      case "sw" => new Sidewinder()
      case "ab" => new AldousBroder()
      case "wi" => new Wilsons()
      case "hk" => new HuntKill()
      case "rb" => new RecursiveBacktracker()
      case "bt" => new BinaryTree()
      case _ => new Wilsons()
    }
    gen.on(grid, None)
  }

  def printMaze[A <: MazeCell](g: Grid[A]): Unit = {
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
