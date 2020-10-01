import org.scalatest.FunSuite

import lib.{MazeCell, GridCell}

class MazeCellTest extends FunSuite {
  test("MazeCell constructor") {
    var cell = new GridCell(2, 3);
    assert(cell.row === 2);
    assert(cell.column === 3);
    assert(cell.links.isEmpty)
    assert(cell.neighbors.length === 0)
  }

  test("Cell linking (bidirectional)") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(1, 1);

    cella.linkBidirectional(cellb);
    assert(cella.isLinked(cellb));
    assert(cellb.isLinked(cella));
    assert(!cella.links.isEmpty);
    assert(!cellb.links.isEmpty);
  }

  test("Cell linking (unidirectional)") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(1, 1);

    cella.link(cellb);
    assert(cella.isLinked(cellb));
    assert(!cellb.isLinked(cella));
    assert(!cella.links.isEmpty);
    assert(cellb.links.isEmpty);
  }

  test("MazeCell neighbors") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(1, 1);

    cella.east = cellb;
    assert(cella.neighbors.length === 1);
  }

  test("distances") {
    var cella = new GridCell(0, 0);
    var cellb = new GridCell(0, 1);
    var cellc = new GridCell(0, 2);

    cella.linkBidirectional(cellb);
    cellb.linkBidirectional(cellc);

    val distances = cella.distances
    assert(distances.get(cella) == 0)
    assert(distances.get(cellb) == 1)
    assert(distances.get(cellc) == 2)
    assert(cellc.distances.get(cella) == 2)
  }
}
