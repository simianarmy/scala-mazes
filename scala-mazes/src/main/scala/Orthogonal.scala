/**
 * Generates square-ish grids
  */
 import lib._

object Orthogonal extends MazeApp {
  val g = conf.shape match {
    case "triangle" => makeTriangleGrid
    case "hex" => makeHexGrid
    case "weave" => makeWeaveGrid
    case "3d" => make3DGrid(3)
    case "sphere" => makeSphereGrid
    // TODO: would be great to be able to include Polar here
    // case "polar" => makePolarGrid
    case _ => makeGrid
  }

  val gg = generateMaze(g)

  printMaze(gg)
}
