import algorithms.RecursiveBacktracker
import lib.OrthogonalGrid

object KillingCells extends MazeApp {
  var g = new OrthogonalGrid(5, 5);

  g.getCell(0, 0).east.west = null
  g.getCell(0, 0).south.north = null
  g.getCell(4, 4).west.east = null
  g.getCell(4, 4).north.south = null

  println(generateMaze(g, algorithm = "rb", startCell = g.getCell(1, 1)))

  println(g)
}
