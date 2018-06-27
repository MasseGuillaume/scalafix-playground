package fix

import scalafix._
import scalafix.util._
import scala.meta._

final case class Scalafixplayground_v1(index: SemanticdbIndex) extends SemanticRule(index, "Scalafixplayground_v1") {

  val breakOutSymbol = 
    SymbolMatcher.exact(
      Symbol("_root_.scala.collection.package.breakOut(Lscala/collection/generic/CanBuildFrom;)Lscala/collection/generic/CanBuildFrom;.")
    )

  override def fix(ctx: RuleCtx): Patch = {
    ctx.tree.collect {
      case i: Importee if breakOutSymbol.matches(i) =>
        ctx.removeImportee(i)

      case Term.Apply(ap @ Term.ApplyInfix(lhs, op, _, List(rhs)), List(breakOutSymbol(breakout))) =>
        ctx.addRight(lhs, ".iterator") +
        ctx.addRight(rhs, ".iterator") +
        ctx.addRight(ap, ".to") +
        ctx.replaceTree(breakout, "implicitly")

      case Term.Apply(ap @ Term.Apply(Term.Select(lhs, _), _), List(breakOutSymbol(breakout))) =>
        ctx.addRight(lhs, ".iterator") +
        ctx.addRight(ap, ".to") +
        ctx.replaceTree(breakout, "implicitly")
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

* [ ] zipAll
* [ ] ++
* [ ] union (rewrite to a.concat(b))
* [ ] collect
* [ ] flatMap
* [ ] map
* [ ] scanLeft
* [ ] zip
* [ ] zipWithIndex

reverse-iterator

* [ ] reverseMap

*/