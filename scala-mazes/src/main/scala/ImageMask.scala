import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask
import scala.util.{Success,Failure}

object ImageMask extends MazeApp {
  if (conf.mask.isEmpty) {
    println("\n*** Please specify a png file to use as a template")
    System.exit(0)
  }
  val mask = Mask.fromPng(conf.mask) match {
    case Success(i) => i
    case Failure(s) => {
      println("fromPng exception: " + s)
      null
    }
  }
  if (mask == null) {
    println("Could not create a mask from file: " + conf.mask)
    System.exit(0)
  }

  val g = new MaskedGrid(mask)
  val gg = generateMaze(g, "rb")

  val filename = "generated/maze-rb-" + conf.mask.split('/').last
  MazeApp.gridToPng(gg, filename)
}
