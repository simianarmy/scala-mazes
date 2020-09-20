import org.scalatest.FunSuite

import lib.{MazeCell, GridCell}

class MazeCellTest extends FunSuite {
  test("MazeCell constructor") {
    var cell = new GridCell(2, 3);
    assert(cell.row === 2);
    assert(cell.column === 3);
    assert(cell.getLinks().isEmpty)
    assert(cell.neighbors.length === 0)
  }

  test("Cell linking (bidirectional)") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(1, 1);

    cella.link(cellb);
    assert(cella.isLinked(cellb));
    assert(cellb.isLinked(cella));
    assert(!cella.getLinks().isEmpty);
    assert(!cellb.getLinks().isEmpty);
  }

  test("Cell linking (unidirectional)") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(1, 1);

    cella.link(cellb, bidi = false);
    assert(cella.isLinked(cellb));
    assert(!cellb.isLinked(cella));
    assert(!cella.getLinks().isEmpty);
    assert(cellb.getLinks().isEmpty);
  }

  test("MazeCell neighbors") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(1, 1);

    cella.east = cellb;
    assert(cella.neighbors.length === 1);
  }
}
