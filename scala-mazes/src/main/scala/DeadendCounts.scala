/**
  * Calculates avg # deadeads per algorithm
  */
import lib._
import algorithms._

import scala.collection.mutable.ArrayBuffer

object DeadendCounts extends MazeApp {
  val tries = 100
  val size = 20

  var averages = collection.mutable.Map[String, Float]()
  var deadendCounts = new ArrayBuffer[Int]()

  MazeApp.AlgorithmIds.foreach(alg => {
    println("running " + MazeApp.generatorNameById(alg))

    deadendCounts.clear()

    for (i <- 0 until tries) {
      var grid = new OrthogonalGrid[GridCell](size, size)
      val gg = generateMaze(grid, alg)

      //TODO: Fix compile errors caused by this
      deadendCounts += gg.deadends.length
    }

    val totalDeadends = deadendCounts.sum
    averages(alg) = totalDeadends / deadendCounts.length
  })

  val totalCells = size * size
  println("Average dead-ends per " + size + "x" + size + " maze (" + totalCells + " cells): ")

  averages.toSeq.sortWith(_._2 > _._2).foreach(item => {
    val name = MazeApp.generatorNameById(item._1)
    val avg = item._2
    val percentage = avg * 100.0 / (size * size)
    println(f"$name%23s" + " : " + f"$avg%1.0f" + "/" + totalCells + " (" + percentage + "%)")
  })
}
