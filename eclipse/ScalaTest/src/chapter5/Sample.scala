package chapter5

object Fib extends App {
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝パターンマッチの基本＝＝＝＝＝＝＝＝＝＝＝＝＝")
  /** 
   * フィボナッチ数列を計算する 
   * @param in: Int フィボナッチ数列の順番
   * @return フィボナッチ数列の内、引数で渡された順番にある数字
   * ・フィボナッチ数列とは：
   * https://matome.naver.jp/odai/2146866504646122901
   * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 ...
   * ・実行例
   * 引数：3の場合→左から３番目の数字を返す→2
   * 引数：8の場合→左から８番目の数字を返す→21
   */
  def fibonacci(in: Int): Int = in match {
    case 0 | -1 | -2 => 0 // 一行で複数の条件を記述可能
    case n if n <= 0 => 0 // if文をパターンガードという。
    case 1 => 1
    case n => fibonacci(n - 1) + fibonacci(n - 2)
  }
  /** ケースの評価は上から順番に行われる */
  def fib2(in: Int): Int = in match {
    case n if n <= 0 => 0 // if文をパターンガードという。マイナスの値が渡されたら０を返す
    case 1 => 1
    case n => fib2(n - 1) + fib2(n - 2)
  }
  println(fibonacci(8))
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝あらゆる型とのマッチング＝＝＝＝＝＝＝＝＝＝＝＝＝")
  /** matchをStringに対して実行 */
  def myMules(name: String) = name match {
    case "Elwood" | "Madeline" => Some("Cat")
    case "Archer" => Some("Dog")
    case "Pumplin" | "Firetruck" => Some("Fish")
    case _ => None
  }
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝もっとパターンマッチ＝＝＝＝＝＝＝＝＝＝＝＝＝")
  /** 「|」を使用して複数の型へのマッチング */
  def test1(in: Any) = in match {
    case 1 => "One"
    case "David" | Some("Dog") => "Walk" // StringかSome(String)の場合
    case _ => "No Clue"
  }
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝型の判断＝＝＝＝＝＝＝＝＝＝＝＝＝")
  /** 型チェック（javaでいうinstanceOf）とキャストを一度に行える */
  def test2(in: Any): String = in match {
    case s: String => "String, length" + s.length
    case i: Int if i>= 0 => "Natural Int: " + i
    case i: Int => "Another Int: " + i
    case a: AnyRef => a.getClass.getName
    case _ => "null"
  }
  println(test2(null))
  println(test2("aaaaa"))
  println(test2(-5))
  println(test2(6))
  println(test2(5L))

  println("＝＝＝＝＝＝＝＝＝＝＝＝＝ケースクラス＝＝＝＝＝＝＝＝＝＝＝＝＝")
  /**
   * ケースクラスの特徴
   * ・toString、hashCode、equalsメソッドを自動的に持つ
   * ・getプロパティと抽出子の機能を備える
   * ・new演算子を使用せずにインスタンスを生成できる
   * ・コンストラクタ引数に渡された値はイミュータブルである
   */
  case class Person(name: String, age: Int, valid: Boolean)
  val p = Person("David", 45, true)
  val m = new Person("newDavid", 50, true) // newを使用してもインスタンス生成可能
  /** コンストラクタ引数に対応するプロパティを持つ */
  println(p.name + p.age + p.valid)
//  p.name = "changed" valとなるのでコンパイルエラーになる
  /**
   * コンストラクタ引数の値をミュータブルにしたい場合。
   * 引数をvar宣言する
   */
  case class MPerson(var name: String, var age: Int)
  val mp = MPerson("cccc", 50)
  println("before: " + mp)
  mp.name = "changed"
  mp.age = 3
  println("after: " + mp)
  
  /** 
   * 35歳以上のPersonかを判定する
   * パターンマッチにcase classを使用可能
   * かつ、抽出子を使用可能
   */
  def older(p: Person): Option[String] = p match {
    case Person(name, age, valid) if age > 35 => Some(name)
    case p if p.age <= 20 => Some("young man")
    case _ => None
  }
  println(older(p))
  println(older(Person("three", 3, true)))
  println(older(Person("twentyfour", 24, true)))
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝リストへのパターンマッチ＝＝＝＝＝＝＝＝＝＝＝＝＝")
  val x = 1
  val rest = List(2, 3, 4)
  /** 
   * scalaではリストの個々の要素を「cons」セルと呼ぶ
   * consセルには格納するデータの参照と、以降の要素への参照を保持する
   * 次の要素の参照は、consセルか要素がない場合はNilオブジェクトが格納されている
   * ::はメソッド名であり、case class名である
   * scalaではconsセルはケースクラス「::」で表される
   */
  x :: rest // これで連結するListを作成できる
  (x :: rest) match {
    case xprime :: restprime => 
      println(xprime)
      println(restprime)
    case Nil => println("nil")
  }
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝パターンマッチとリスト＝＝＝＝＝＝＝＝＝＝＝＝＝")
  /**
   * 与えられたListの奇数を合計して返す
   * パターンマッチでは先頭の要素を取得することができる
   * @param in: List[Int] 計算対象のリスト
   * @return 与えられたListの奇数合計値
   */
  def sumOdd(in: List[Int]): Int = in match {
    case Nil => 0 // listが空の場合は0を返す
    case x :: rest if x % 2 == 1 =>
      println("Odd===========")
      println("x: " + x)
      println("rest: " + rest)
      x + sumOdd(rest) // listの先頭が奇数ならば、先頭と、先頭以降のlistの内、奇数の合計値を足した値を返す
    case _ :: rest =>
      println("Even===========")
      println("rest: " + rest)
      sumOdd(rest) // listの先頭が偶数ならば、先頭は無視し、先頭以降のlistの内、奇数の合計値を足した値を返す
  }
  println(sumOdd(List(1, 3, 5, 8, 7)))
   
  /**
   * 隣り合う要素が同じ場合、１つにまとめたリストを返却します。
   * パターンマッチでは先頭のみでなく、リストの特定の要素にマッチさせることができる
   * @param in: List[T] 計算対象のリスト
   * @return 隣り合う要素が同じ場合に１つにまとめた結果のリスト
   */
  def noPairs[T](in: List[T]): List[T] = in match {
    case Nil => Nil
    case a :: b :: rest if a == b => 
      // リストの初めの２つの要素が同じ場合、重複する要素を除いたリストを引数に
      // noPairsを呼び出します
      noPairs(a :: rest)
    case a :: rest => 
      // １番目の要素とリストの残りを引数にnoParisを呼び出した結果を連結して返します
      a :: noPairs(rest)
  }
  println("resutl noPairs: " + noPairs(List("aaaa", "aaaa", "yy", "z", "yy")))
  println("resutl noPairs: " + noPairs(List(1,2,3,3,3,4,1,1)))
  
  /**
   * 文字列"ignore"の直前の要素を除外したリストを返します。
   * パターンマッチではリテラルに対しても実行できる
   * ・参考書に記載の通り。これだとignoreも除外されるから微妙に説明と違う
   * @param in: List[String] 計算対象のリスト
   * @return 文字列"ignore"の直前の要素を除外したリスト
   */
  def ignore(in: List[String]): List[String] = in match {
    case Nil => Nil
    case x :: "ignore" :: rest => 
      // リスト中の２番目の要素が"ignore"だった場合、"ignore"より後の
      // リストを引数にignoreメソッドを呼び出す
      println("ignore========")
      println("x: " + x)
      println("rest: " + rest)
      ignore(rest)
    case x :: rest => 
      // リストの１番目の要素に、リストの２番目意向を引数に
      // ignoreメソッドを呼び出した結果を連結したリストを返します。
      println("not ignore========")
      println("x: " + x)
      println("rest: " + rest)
      x :: ignore(rest)
  }
  println("result ignore: " + ignore(List("aaaa", "bbbbb", "ignore", "cccc", "ddddd")))
  
  /** 
   * String型のみを抽出しリストを返します。
   * パターンマッチでは型チェック／キャスト機構を用いることもできる
   * @param in: List[Any] 複数の型の要素が格納されたリスト
   * @return 渡されたリストからStringだけを抽出したリスト
   */
  def getString(in: List[Any]): List[String] = in match {
    case Nil => Nil
    case (s: String) :: rest => s :: getString(rest)
    case _ :: rest => getString(rest)
  }
  println("result getString: " + getString(List(1, 5, "Stringだよ", 6 , "２つめのStringだよ")))
  
  println("＝＝＝＝＝＝＝＝＝＝＝＝＝ケースクラスでのネストしたパターンマッチ＝＝＝＝＝＝＝＝＝＝＝＝＝")

  /*
   * 参考書ではcase classのPersonを継承して新たにcase classを定義したのち
   * パターンマッチで親クラスと子クラスのマッチをしていたが、
   * どうやらcase classを継承したcase classは禁止されたようだ
   * @see http://sakamoto.hatenablog.com/entry/2012/09/08/215509
   */
//  class ParentPerson(name: String, age: Int, valid: Boolean)
//  class EPerson(name: String, age: Int, valid: Boolean, p: Person) extends ParentPerson(name, age, valid)
//  case class MarriedPerson(name: String, age: Int, valid: Boolean, spurse: Person)
//  val sally = MarriedPerson("sally", 35, true, Person("David", 45, true))
//  def mOlder(p: Person): Option[String] = p match {
//    case Person(name, age, true) if age > 35 => Some(name)
//    case EPerson(name, _, _, Person(_, age, true)) if age > 35 => Some(name)
//    case _ None
//  }
}