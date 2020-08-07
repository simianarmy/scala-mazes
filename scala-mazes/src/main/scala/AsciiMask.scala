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

  val filename = "generated/maze-rb-" + g.rows + "x" + g.columns + ".png"
  javax.imageio.ImageIO.write(
    g.toPng(),
    "png",
    new java.io.File(filename)
  )
  println("image saved to " + filename)
}
