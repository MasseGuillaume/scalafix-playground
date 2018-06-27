


package fix


class Playground() {
  val xs = List(1, 2, 3)
  xs.iterator.map(_ + 1).to(implicitly): Set[Int]
  xs.iterator.zipAll(xs, 0, 0).to(implicitly): Array[(Int, Int)]
  (xs.iterator ++ xs.iterator).to(implicitly): Set[Int]
}
