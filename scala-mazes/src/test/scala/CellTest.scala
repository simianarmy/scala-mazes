import org.scalatest.FunSuite


class CellTest extends FunSuite {
  test("Cell constructor") {
    var cell = new Cell(2, 3);
    assert(cell.row === 2);
    assert(cell.column === 3);
    assert(cell.getLinks().isEmpty)
    assert(cell.neighbors().length === 0)
  }

  test("Cell linking (bidirectional)") {
    var cella = new Cell(0, 0);
    var cellb = new Cell(1, 1);

    cella.link(cellb);
    assert(cella.isLinked(cellb));
    assert(cellb.isLinked(cella));
    assert(!cella.getLinks().isEmpty);
    assert(!cellb.getLinks().isEmpty);
  }

  test("Cell linking (unidirectional)") {
    var cella = new Cell(0, 0);
    var cellb = new Cell(1, 1);

    cella.link(cellb, bidi = false);
    assert(cella.isLinked(cellb));
    assert(!cellb.isLinked(cella));
    assert(!cella.getLinks().isEmpty);
    assert(cellb.getLinks().isEmpty);
  }

  test("Cell neighbors") {
    var cella = new Cell(0, 0);
    var cellb = new Cell(1, 1);

    cella.east = cellb;
    assert(cella.neighbors().length === 1);
  }
}
