import algorithms.RecursiveBacktracker
import lib.MaskedGrid
import lib.Mask

object SimpleMask extends MazeApp {
  var m = new Mask(5, 5)

  m(0)(0) = false
  m(2)(2) = false
  m(4)(4) = false

  var g = new MaskedGrid(m)
  val gg = generateMaze(g, "rb")

  println(gg)
}
