package fix

import scalafix._
import scalafix.util._
import scala.meta._

final case class Scalafixplayground_v1(index: SemanticdbIndex) extends SemanticRule(index, "Scalafixplayground_v1") {
  // println(ctx.index.symbol())

  val breakOutSymbol = 
    SymbolMatcher.exact(
      Symbol("_root_.scala.collection.package.breakOut(Lscala/collection/generic/CanBuildFrom;)Lscala/collection/generic/CanBuildFrom;.")
    )

  val seqLikeUnion = 
    SymbolMatcher.exact(
      Symbol("_root_.scala.collection.SeqLike#union(Lscala/collection/GenSeq;Lscala/collection/generic/CanBuildFrom;)Ljava/lang/Object;.")
    )

  val seqLikeReverseMap =
    SymbolMatcher.exact(
      Symbol("_root_.scala.collection.SeqLike#reverseMap(Lscala/Function1;Lscala/collection/generic/CanBuildFrom;)Ljava/lang/Object;.")
    )

  override def fix(ctx: RuleCtx): Patch = {

    def fixIt(lhs: Term, ap: Term, breakout: Tree, isReversed: Boolean = false): Patch = {
      val iterator =
        if(isReversed) "reverseIterator"
        else "iterator"

      ctx.addRight(lhs, "." + iterator) +
      ctx.addRight(ap, ".to") +
      ctx.replaceTree(breakout, "implicitly")
    }

    ctx.tree.collect {
      case i: Importee if breakOutSymbol.matches(i) =>
        ctx.removeImportee(i)

      case Term.Apply(ap @ Term.ApplyInfix(lhs, op, _, _), List(breakOutSymbol(breakout))) =>
        fixIt(lhs, ap, breakout)

      case Term.Apply(ap @ Term.Apply(Term.Select(lhs, op), _), List(breakOutSymbol(breakout))) =>
        val replaceUnion =
          if (seqLikeUnion.matches(op)) ctx.replaceTree(op, "concat")
          else Patch.empty

        val isReversed = seqLikeReverseMap.matches(op)
        val replaceReverseMap = 
          if (isReversed) ctx.replaceTree(op, "map")
          else Patch.empty
          
        fixIt(lhs, ap, breakout, isReversed) + replaceUnion + replaceReverseMap

      case Term.Apply(ap @ Term.Apply(Term.Apply(Term.Select(lhs, _), _), _), List(breakOutSymbol(breakout))) =>
        fixIt(lhs, ap, breakout)

    }.asPatch
  }
}


/*

view

* [ ] +:
* [ ] :+
* [ ] ++:
* [ ] updated

iterator

* [x] zipAll
* [x] ++
* [x] map
* [x] collect
* [x] flatMap
* [x] scanLeft
* [x] zip
* [x] zipWithIndex
  zipWithIndex: CanBuildFrom[From, (T, Int), To]): To
  breakOut:     CanBuildFrom[From,  T      , To]
* [x] union (rewrite to a.concat(b))

reverse-iterator

* [ ] reverseMap

*/