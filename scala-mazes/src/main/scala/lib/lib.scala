package object lib {
  import scala.collection.mutable.ArrayBuffer

  trait Cells {
    type C
    var list: ArrayBuffer[C]
  }
}

