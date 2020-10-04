import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask
import scala.util.{Success,Failure}

object AsciiMask extends MazeApp {
  if (conf.mask == null) {
    println("\n*** Please specify a text file to use as a template (from " + System.getProperty("user.dir"))
    System.exit(0)
  }
  val mask = Mask.fromTxt(conf.mask) match {
    case Success(i) => i
    case Failure(s) => {
      println("Could not create a mask from file: " + conf.mask + " " + s)
      System.exit(0)
      null
    }
  }

  val g = new MaskedGrid(mask)
  val gg = generateMaze(g, "rb")

  printMaze(gg)
}
