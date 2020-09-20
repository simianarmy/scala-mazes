import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask
import scala.util.{Success,Failure}

object AsciiMask extends MazeApp {
  if (args.length < 1) {
    println("\n*** Please specify a text file to use as a template")
    System.exit(0)
  }
  val mask = Mask.fromTxt(args(0)) match {
    case Success(i) => i
    case Failure(s) => null
  }
  if (mask == null) {
    println("Could not create a mask from file: " + args(0))
    System.exit(0)
  }

  var g = new MaskedGrid(mask)
  val gg = generateMaze(g)

  printMaze(gg)
}
