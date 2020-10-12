import lib.Grid3D

object Grid3DApp extends MazeApp {
  val g = make3DGrid(3)
  val gg = generateMaze(g)

  val filename = "generated/maze-" + gg.id + "-" + conf.alg + "-" + g.rows + "x" + g.columns + ".png"
  javax.imageio.ImageIO.write(
    gg.toPng(20),
    "png",
    new java.io.File(filename)
  )
  println("image saved to " + filename)
}

