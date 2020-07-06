/**
  * Utility to generate grid images and write them to png files
  */
object PngWriter {
  def saveGrid(grid: Grid, filename: String): Unit = {
    javax.imageio.ImageIO.write(
      grid.toPng(),
      "png",
      new java.io.File(filename)
    )
    println("image saved to " + filename)
  }
}
