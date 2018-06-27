


package fix


class Playground() {
  val xs = List(1, 2, 3)
  xs.iterator.map(_ + 1).to(implicitly): Set[Int]
  xs.iterator.zipAll(xs.iterator, 0, 0).to(implicitly): Array[(Int, Int)]
  xs.iterator.zip(xs.iterator).to(implicitly): Array[(Int, Int)]
  (xs.iterator ++ xs.iterator).to(implicitly): Set[Int]
  xs.iterator.collect{ case x => x }.to(implicitly): Set[Int]
  xs.iterator.flatMap(x => List(x)).to(implicitly): Set[Int]
  xs.iterator.scanLeft(0)((a, b) => a + b).to(implicitly): Set[Int]
}
