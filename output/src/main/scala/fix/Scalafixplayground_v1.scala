


package fix


class Playground() {
  val xs = List(1, 2, 3)
  xs.iterator.map(_ + 1).to(implicitly): Set[Int]
  xs.iterator.zipAll(xs, 0, 0).to(implicitly): Array[(Int, Int)]
  xs.iterator.zip(xs).to(implicitly): Array[(Int, Int)]
  (xs.iterator ++ xs).to(implicitly): Set[Int]
  xs.iterator.collect{ case x => x }.to(implicitly): Set[Int]
  xs.iterator.flatMap(x => List(x)).to(implicitly): Set[Int]
  xs.iterator.scanLeft(0)((a, b) => a + b).to(implicitly): Set[Int]
  xs.iterator.concat(xs).to(implicitly): Set[Int]
  xs.reverseIterator.map(_ + 1).to(implicitly): Set[Int]
}

