/**
  * Utility to generate grid images and write them to png files
  */
package lib

object PngWriter {
  def saveGrid(grid: Grid[_,_], filename: String): Unit = {
    javax.imageio.ImageIO.write(
      grid.toPng(),
      "png",
      new java.io.File(filename)
    )
    println("image saved to " + filename)
  }
}

