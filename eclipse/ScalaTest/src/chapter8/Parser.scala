package chapter8

object parser extends App {
  
  println("-----高階関数-----")

  // 一例：map
  val ret = List(1, 2, 3).map(_ + 1)
  println(ret)
  
  // 合成関数
  def plus1(in: Int) = in + 1
  def twice(in: Int) = in * 2

  // andThen：Functionトレイトのメソッド
  // 「plus1 _ 」 を実行した結果をtwiceに渡し、twiceを実行する
  val addDouble = plus1 _ andThen twice
  val ret2 = List(1, 2, 3).map(addDouble)
  println(ret2)
  
  // コンビネータ
  // 他の関数を引数に、関数を返す関数のこと 関数だらけ。。
  def parse = (elem('t')~elem('r')~elem('u')~elem('e')) | (elem('f')~elem('a')~elem('l')~elem('s')~elem('e'))
  def p2 = ('t'~'r'~'u'~'e') | ('f'~'a'~'l'~'s'~'e')
  // ^^^：　マッチした場合の戻り値を指定できる。
  def p3: Parser[Boolean] = ('t'~'r'~'u'~'e' ^^^ true) | ('f'~'a'~'l'~'s'~'e' ^^^ false)
  def p4: Parser[Boolean] = ('t'~'r'~'u'~'e') | ('f'~'a'~'l'~'s'~'e')
  
  def positiveDigit = elem('1') | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' | 
  def digit = positiveDigit | '0'
  // ^^：　マッチした場合にパターンに含まれる文字の一部を返却できる
  def long1: Parser[Long] = positiveDigit ~ rep(digit) ^^ {
    case (first: Char) ~ (rest: List[Char]) => (first :: rest).mkString.toLong
  }
  // ^?：マッチさせる前に条件を設定できる
  lazy val logn2: Parser[Long] = positiveDigit ~ req(digit) ^? {
    case first ~ rest if rest.length < 18 => (first :: rest).mkString.toLong
  }
}

