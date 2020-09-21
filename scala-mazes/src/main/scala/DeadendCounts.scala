/**
  * Calculates avg # deadeads per algorithm
  */
import lib.OrthogonalGrid
import lib.Grid
import algorithms._

import scala.collection.mutable.ArrayBuffer

object DeadendCounts extends MazeApp {
  val tries = 100
  val size = 20

  var averages = collection.mutable.Map[String, Float]()
  val algorithms = Map[String, String](
    "Sidewinder" -> "sw",
    "Aldous-Broder" -> "ab",
    "Wilsons" -> "wi",
    "Hunt-and-kill" -> "hk",
    "Binary Tree" -> "bt",
    )
  var deadendCounts = new ArrayBuffer[Int]()

  algorithms.foreach(alg => {
    println("running " + alg._1)

    deadendCounts.clear()

    for (i <- 0 until tries) {
      var grid = new OrthogonalGrid(size, size)
      val gg = generateMaze(grid, alg._2)

      //TODO: Fix compile errors caused by this
      deadendCounts += gg.deadends().length
    }

    val totalDeadends = deadendCounts.sum
    averages(alg._1) = totalDeadends / deadendCounts.length
  })

  val totalCells = size * size
  println("Average dead-ends per " + size + "x" + size + " maze (" + totalCells + " cells): ")

  averages.toSeq.sortWith(_._2 > _._2).foreach(item => {
    val name = item._1
    val avg = item._2
    val percentage = avg * 100.0 / (size * size)
    println(f"$name%14s" + " : " + f"$avg%1.0f" + "/" + totalCells + " (" + percentage + "%)")
  })
}
