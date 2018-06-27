/*
rule = "class:fix.Scalafixplayground_v1"
*/
package fix

import scala.collection.breakOut

class Playground() {
  val xs = List(1, 2, 3)
  xs.map(_ + 1)(breakOut): Set[Int]
  xs.zipAll(xs, 0, 0)(breakOut): Array[(Int, Int)]
  xs.zip(xs)(breakOut): Array[(Int, Int)]
  (xs ++ xs)(breakOut): Set[Int]
  xs.collect{ case x => x }(breakOut): Set[Int]
  xs.flatMap(x => List(x))(breakOut): Set[Int]
  xs.scanLeft(0)((a, b) => a + b)(breakOut): Set[Int]
}