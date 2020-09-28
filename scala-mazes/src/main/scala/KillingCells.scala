import algorithms.RecursiveBacktracker
import lib.OrthogonalGrid
import lib.{Mask, MaskedGrid}

object KillingCells extends MazeApp {
 val mask = new Mask(5, 5)

 mask(0)(0) = false
 mask(2)(2) = false
 mask(4)(4) = false

 val g = new MaskedGrid(mask)
 printMaze(generateMaze(g, "rb"))
}
