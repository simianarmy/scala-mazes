import mazes.Grid
import algorithms.BinaryTree

object BinaryTreeDemo extends App {
  var g = new Grid(4, 4);
  g = BinaryTree.on(g);
  println("Binary Tree Demo");
  println(g);
}

