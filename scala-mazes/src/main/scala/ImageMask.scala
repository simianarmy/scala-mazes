import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask

object ImageMask extends App {
  if (args.length < 1) {
    println("\n*** Please specify a png file to use as a template")
    System.exit(0)
  }
  val mask = Mask.fromPng(args(0))
  var g = new MaskedGrid(mask)
  g = RecursiveBacktracker.on(g)

  val filename = "generated/maze-rb-" + args(0).split('/').last
  javax.imageio.ImageIO.write(
    g.toPng(5),
    "png",
    new java.io.File(filename)
  )
  println("image saved to " + filename)
}
