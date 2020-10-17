import scopt.OParser
import java.io.File
import java.awt.Color

import lib._
import algorithms._

object MazeApp {
  val AlgorithmIds = List("ab", "bt", "el", "gt", "hk", "rb", "rd", "sw", "pr1", "pr", "wi")
  val ShapeIds = List("square", "polar", "hex", "triangle", "weave", "3d", "sphere")

  case class Config(
    rows: Int = 4,
    cols: Int = 4,
    alg: String = "wi",
    shape: String = "square",
    ascii: Boolean = false,
    color: Color = Color.WHITE,
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
        .validate(x => if (AlgorithmIds contains x) success else failure("alg must be one of " + (AlgorithmIds mkString ", ")))
        .action((x, c) => c.copy(alg = x))
        .text("alg is one of (" + AlgorithmIds.mkString(", ") + ")"),
      opt[String]('s', "shape")
        .valueName("<shape>")
        .validate(x => if (ShapeIds contains x) success else failure("shape must be one of " + (ShapeIds mkString ", ")))
        .action((x, c) => c.copy(shape = x))
        .text("shape is one of (" + ShapeIds.mkString(", ") + ")"),
      opt[Unit]("ascii")
        .action((_, c) => c.copy(ascii = true)),
      opt[String]("color")
        .action((x, c) => c.copy(color = x match {
          case "red" => Color.RED
          case "green" => Color.GREEN
          case "blue" => Color.BLUE
          case "yellow" => Color.YELLOW
          case "orange" => Color.ORANGE
          case _ => Color.WHITE
        }))
        .text("color is one of (red, green, blue, yellow, orange)"),
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

  def isGridOnlyAlgorithm(alg: String): Boolean = {
    alg == "bt" || alg == "sw"
  }

  def validatePolarArgs(alg: String): Boolean = !isGridOnlyAlgorithm(alg)

  def validateArgs(shape: String, alg: String): Boolean = shape match {
    case "polar" => validatePolarArgs(alg)
    case _ => true
  }

  def generatorById(id: String): MazeGenerator = id match {
      case "ab" => new AldousBroder()
      case "bt" => new BinaryTree()
      case "el" => new Ellers()
      case "gt" => new GrowingTree()
      case "hk" => new HuntKill()
      case "pr1" => new Prims()
      case "pr" => new TruePrims()
      case "rb" => new RecursiveBacktracker()
      case "rd" => new RecursiveDivision()
      case "wi" => new Wilsons()
      case "sw" => new Sidewinder()
    }

  def generatorNameById(id: String): String = generatorById(id).toString

  def gridToPng[A <: MazeCell](grid: Grid[A], filename: String, inset: Double = 0) = {
    val realInset = grid match {
        case wg: WeaveGrid => 0.2
        case _ => inset
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

  def debugMaze[A <: MazeCell](grid: Grid[A]) = {
    debug(grid.toString)
  }

  def makeGrid = {
    if (conf.rainbow) new OrthogonalGrid[GridCell](rows, cols) with RainbowColored[GridCell]
    else new OrthogonalGrid[GridCell](rows, cols) with Colored[GridCell]
  }

  def makeTriangleGrid = {
    if (conf.rainbow) new TriangleGrid(rows, cols) with RainbowColored[TriangleCell]
    else new TriangleGrid(rows, cols) with Colored[TriangleCell]
  }

  def makeHexGrid = {
    if (conf.rainbow) new HexGrid(rows, cols) with RainbowColored[HexCell]
    else new HexGrid(rows, cols) with Colored[HexCell]
  }

  def makeWeaveGrid = {
    //if (conf.rainbow) new WeaveGrid(rows, cols) with RainbowColored[OverCell]
    //else new WeaveGrid(rows, cols) with Colored[OverCell]
    new WeaveGrid(rows, cols)
  }

  def makePolarGrid = {
    if (conf.rainbow) new PolarGrid(rows) with RainbowColored[PolarCell]
    else new PolarGrid(rows) with Colored[PolarCell]
  }

  def make3DGrid(levels: Int) = {
    new Grid3D(levels, rows, cols)
  }

  def makeSphereGrid = {
    new SphereGrid(20)
  }

  /**
    * The crux of this app - we have to support any combination of shape and algorithm and restrict
    * unsupported combinations, such as non-GridCell grids with Grid-only algorithms
    *
    * @param grid
    * @param algorithm
    * @return grid : cells connected into maze
    */
  def generateMaze[A <: MazeCell](grid: Grid[A], algorithm: String = conf.alg): Grid[A] = {
    debug("generating " + MazeApp.generatorNameById(algorithm))

    if (!MazeApp.validateArgs(conf.shape, algorithm)) {
      println("Unsupported maze shape / algorithm!  Please try again.")
      return grid
    }
    MazeApp.generatorById(algorithm).on(grid, None)()
  }

  /**
    * Prints ASCII or PNG version of the grid
    *
    * @param g
    * @param toAscii
    * @param inset
    */
  def printMaze[A <: MazeCell](g: Grid[A], toAscii: Boolean = conf.ascii, inset: Double = 0): Unit = {
    if (toAscii) {
      println(g);
    } else {
      g.setColor(conf.color)

      // draw image to a file
      val filename = "generated/maze-" + g.id + "-" + conf.alg + "-" + g.rows + "x" + g.columns + ".png"
      MazeApp.gridToPng(g, filename, inset)
    }
  }
}
