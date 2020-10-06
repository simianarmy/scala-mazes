import scopt.OParser
import java.io.File

import lib._
import algorithms._

object MazeApp {
  val AlgorithmIds = List("ab", "bt", "hk", "rb", "sw", "pr1", "pr", "wi")
  val ShapeIds = List("square", "polar", "hex", "triangle", "weave")

  case class Config(
    rows: Int = 4,
    cols: Int = 4,
    alg: String = "wi",
    shape: String = "square",
    ascii: Boolean = false,
    rainbow: Boolean = false,
    braid: Double = 0,
    debug: Boolean = false,
    mask: String = ""
  )

  val builder = OParser.builder[Config]
  val parser1 = {
    import builder._
    OParser.sequence(
      programName("scala-mazes"),
      head("scala-mazes", "0.1"),
      // option -f, --foo
      opt[Int]('r', "rows")
        .action((x, c) => c.copy(rows = x))
        .text("rows is an integer property"),
      opt[Int]('c', "cols")
        .action((x, c) => c.copy(cols = x))
        .text("cols is an integer property"),
      opt[String]('a', "alg")
        .valueName("<alg>")
        .validate(x => if (AlgorithmIds contains x) success else failure("alg must be one of " + AlgorithmIds mkString ", "))
        .action((x, c) => c.copy(alg = x))
        .text("alg is one of (" + AlgorithmIds.mkString(", ") + ")"),
      opt[String]('s', "shape")
        .valueName("<shape>")
        .validate(x => if (ShapeIds contains x) success else failure("shape must be one of " + ShapeIds mkString ", "))
        .action((x, c) => c.copy(shape = x))
        .text("shape is one of (" + ShapeIds.mkString(", ") + ")"),
      opt[Unit]("ascii")
        .action((_, c) => c.copy(ascii = true)),
      opt[Unit]("rainbow")
        .action((_, c) => c.copy(rainbow = true)),
      opt[Double]('b', "braid")
        .action((x, c) => c.copy(braid = x))
        .text("braid is a double property"),
      opt[String]('m', "mask")
        .action((x, c) => c.copy(mask = x))
        .text("mask is a file"),
      opt[Unit]("debug")
        .hidden()
        .action((_, c) => c.copy(debug = true))
        .text("this option is hidden in the usage text"),
      help("help").text("prints this usage text"),
      )
  }

  def config(args: Array[String]): Config = OParser.parse(parser1, args, Config()).getOrElse(Config())

  def generatorById(id: String): GeneralGenerator = id match {
      case "sw" => new Sidewinder()
      case "ab" => new AldousBroder()
      case "hk" => new HuntKill()
      case "rb" => new RecursiveBacktracker()
      case "bt" => new BinaryTree()
      case "pr1" => new Prims()
      case "pr" => new TruePrims()
      case _ => new Wilsons()
    }

  def generatorNameById(id: String): String = generatorById(id).toString

  def gridToPng[A <: MazeCell](grid: Grid[A], filename: String, inset: Double = 0) = {
    val realInset = grid match {
        case wg: WeaveGrid => 0.2
        case _ => inset
      }
    // generate distances for the coloring
    if (grid.distances == null) {
      grid.distances = grid.getCell(grid.rows / 2, grid.columns / 2).distances
    }

    javax.imageio.ImageIO.write(
      grid.toPng(inset = realInset),
      "png",
      new java.io.File(filename)
    )
    println("image saved to " + filename)
  }
}

class MazeApp extends App {
  val conf = MazeApp.config(args)

  val rows: Int = conf.rows
  val cols: Int = conf.cols

  def debug(line: String): Unit = if (conf.debug) println(line) else ()

  def debugMaze[A <: MazeCell](grid: Grid[A], algorithm: String = conf.alg) = {
    debug("braid: " + conf.braid)
    debug("algorithm: " + MazeApp.generatorNameById(algorithm))
    debug(grid.toString)
  }

  def makeGrid = {
    if (conf.rainbow) new OrthogonalGrid[GridCell](rows, cols) with RainbowColored[GridCell]
    else new OrthogonalGrid[GridCell](rows, cols) with Colored[GridCell]
  }

  def makeWeaveGrid = {
    if (conf.rainbow) new WeaveGrid(rows, cols) with RainbowColored[OverCell]
    else new WeaveGrid(rows, cols) with Colored[OverCell]
  }

  def generateMaze[A <: MazeCell](grid: Grid[A], algorithm: String = conf.alg): Grid[A] = {
    grid.braid(conf.braid)
    MazeApp.generatorById(algorithm).on(grid, None)
  }

  def printMaze[A <: MazeCell](g: Grid[A], toAscii: Boolean = conf.ascii, inset: Double = 0): Unit = {
    if (toAscii) {
      println(g);
    } else {
      // draw image to a file
      val filename = "generated/maze-" + g.id + "-" + conf.alg + "-" + g.rows + "x" + g.columns + ".png"
      MazeApp.gridToPng(g, filename, inset)
    }
  }
}
