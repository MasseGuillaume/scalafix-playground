/*
rule = "class:fix.Scalafixplayground_v1"
*/
package fix

import scala.collection.breakOut

class Playground() {
  val xs = List(1, 2, 3)
  xs.map(_ + 1)(breakOut): Set[Int]
  xs.zipAll(xs, 0, 0)(breakOut): Array[(Int, Int)]
  (xs ++ xs)(breakOut): Set[Int]
}