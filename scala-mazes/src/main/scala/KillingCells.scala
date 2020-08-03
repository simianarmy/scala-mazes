import algorithms.RecursiveBacktracker
import lib.Grid

object KillingCells extends MazeApp {
  var g = new Grid(5, 5);

  g.getCell(0, 0).east.west = null
  g.getCell(0, 0).south.north = null
  g.getCell(4, 4).west.east = null
  g.getCell(4, 4).north.south = null

  g = RecursiveBacktracker.on(g, g.getCell(1, 1))

  println(g)
}
