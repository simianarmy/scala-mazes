import mazes.Cell
import mazes.Grid

object Main extends App {
  var cella = new Cell(2, 3);
  var cellb = new Cell(1, 2);
  println( "cell a ", cella);
  println( "cell b ", cellb);
  cella.link(cellb);
  println("a linked to b? ", cella.isLinked(cellb));
  println("b linked to a? ", cellb.isLinked(cella));

  var g = new Grid(3, 3);
  println("grid " + g);
}

