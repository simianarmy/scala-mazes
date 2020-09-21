import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask
import scala.util.{Success,Failure}

object ImageMask extends MazeApp {
  if (args.length < 1) {
    println("\n*** Please specify a png file to use as a template")
    System.exit(0)
  }
  val mask = Mask.fromPng(args(0)) match {
    case Success(i) => i
    case Failure(s) => {
      println("fromPng exception: " + s)
      null
    }
  }
  if (mask == null) {
    println("Could not create a mask from file: " + args(0))
    System.exit(0)
  }

  val g = new MaskedGrid(mask)
  val gg = generateMaze(g, "rb")

  val filename = "generated/maze-rb-" + args(0).split('/').last
  MazeApp.gridToPng(gg, filename)
}
