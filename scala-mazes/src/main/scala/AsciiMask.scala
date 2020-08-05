import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask

object AsciiMask extends App {
  if (args.length < 1) {
    println("\n*** Please specify a text file to use as a template")
    System.exit(0)
  }
  val mask = Mask.fromTxt(args(0))
  var g = new MaskedGrid(mask)
  g = RecursiveBacktracker.on(g)

  println(g)
}
