package fix

import scalafix._
import scalafix.util._
import scala.meta._


import scala.meta._

final case class Scalafixplayground_v1(index: SemanticdbIndex) extends SemanticRule(index, "Scalafixplayground_v1") {

  val valueTypes = List("Byte", "Char", "Int", "Short", "Double", "Float", "Long")
  val valuePlusStringSymbols = valueTypes.map(vt => Symbol(s"scala.$vt#`+`(String)."))
  val valuePlusString = SymbolMatcher.normalized(valuePlusStringSymbols: _*)  

  override def fix(ctx: RuleCtx): Patch = {
    ctx.tree.collect {
      case Term.ApplyInfix(lhs, valuePlusString(_), Nil, List(_)) => ctx.addRight(lhs, ".toString")
    }.asPatch
  }
}
