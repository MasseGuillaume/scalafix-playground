package fix

import scalafix._
import scalafix.util._
import scala.meta._

final case class Scalafixplayground_v1(index: SemanticdbIndex) extends SemanticRule(index, "Scalafixplayground_v1") {
  override def fix(ctx: RuleCtx): Patch = {
    val mutMapUpdate = 
      SymbolMatcher.exact(
        Symbol("_root_.scala.collection.mutable.MapLike#updated(Ljava/lang/Object;Ljava/lang/Object;)Lscala/collection/mutable/Map;.")
      )

    ctx.tree.collect {
      case Term.Apply(Term.Select(a, up @ mutMapUpdate(_)), List(k, v)) => {
        ctx.addRight(up, "clone() += (") +
        ctx.removeTokens(up.tokens) +
        ctx.addRight(v, ")")
      }
    }.asPatch
  }
}