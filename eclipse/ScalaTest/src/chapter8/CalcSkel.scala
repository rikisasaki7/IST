package chapter8
import scala.util.parsing.combinator._

trait RunParser {
  this: RegexParsers =>
  type RootType
  def root: Parser[RootType]
  def run(in: String): ParseResult[RootType] = parseAll(root, in)
}

object CalcSkel extends JavaTokenParsers with RunParser {

  // BNF記述
//  <sumExpr> ::= <prodExpr> [("+" <prodExpr>) | ("-" <prodExpr>)]
//	<prodExpr> ::= <factor> [("*" <factor>) | ("/" <factor>)]
//	<factor> ::= <float> | ("("<subExpr>")")
  
  // 上記BNFをScalaで記述　ー　出力結果未加工版
//  lazy val sumExpr = multExpr ~ rep("+" ~ multExpr | "-" ~ multExpr)
//  lazy val multExpr = factor ~ rep("*" ~ factor | "/" ~ factor)
//  lazy val factor: Parser[Any] = floatingPointNumber | "(" ~ sumExpr ~ ")"

  // 上記BNFをScalaで記述　ー　出力結果加工版
  // rep：　引数をパースした結果をListに変換して返却する
  // ~>：　マッチした右側だけをrep関数の引数に渡す
  lazy val sumExpr = multExpr ~ 
    rep("+" ~> multExpr ^^ (d => (x: Double) => x + d) |
        "-" ~> multExpr ^^ (d => (x: Double) => x - d)) ^^ {
      case seed ~ fs => fs.foldLeft(seed)((a, f) => f(a))
  }
  
  lazy val multExpr = factor ~ 
    rep("*" ~> multExpr ^^ (d => (x: Double) => x * d) |
        "/" ~> multExpr ^^ (d => (x: Double) => x / d)) ^^ {
      case seed ~ fs => fs.foldLeft(seed)((a, f) => f(a))
  }
  lazy val factor: Parser[Double] = 
    floatingPointNumber ^^ (_.toDouble) | "(" ~> sumExpr <~ ")"
  
  type RootType = Any
  def root = {
    val ret = sumExpr
    println(ret)
    ret
  }
  
  // パーステスト
  CalcSkel.run("1")
  CalcSkel.run("1 + 1")
  CalcSkel.run("1 + 1 / 17")
  CalcSkel.run("(1 + 1) / 17")
  
}