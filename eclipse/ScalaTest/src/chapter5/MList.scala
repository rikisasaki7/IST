package chapter5

/** 
 * パターンマッチの内部を知る
 * 
 * 独自のListクラスを定義
 * [+T]：MListのサブクラスが共変性を持つことを表す 
 */
class MList[+T] {
  /** 
   * consセルを表すcaseクラスと同名のメソッドにすることで、リストを連続して作成できる
   * B >: T　→　BはTと同じ、またはTのスーパークラスでなければならない
   * @param x: B 要素
   * @return MList[B] 後続リストの参照
   */
  def ?:[B >: T](x: B): MList[B] = new ?:(x, this)
}

/**
 * 独自定義のNilシングルトンオブジェクト
 * Nothingは全てのクラスのサブクラス
 */
case object MNil extends MList[Nothing]

/** 
 * 独自MListのconsセルを表すクラス
 * 要素と、後続のリストへの参照を保持する
 */
case class ?:[T](hd: T, tail: MList[T]) extends MList[T]

object pattern extends App {
  // Nothingは全てのクラスのサブクラスのため、Intを引数にするとMList[Int]が返される
  val tmp = MNil.?:(1)
  val tmp2 = ?:(1, tmp)
  val tmp3 = ?:(2, tmp)
  println(tryMList(tmp))
  println(tryMList(tmp2))
  println(tryMList(tmp3))
  /** 
   * 独自Listクラスのパターンマッチ 
   * Scalaでは、メソッド名の最後が「：」で終わっている場合、評価の順番が右から左になる性質がある。
   * その性質を利用し、パターンマッチを行なっている。
   */
  def tryMList(in: MList[Any]) = in match {
    case 1 ?: MNil => "foo" // MNil.?:(1)とマッチする場合
    case 1 ?: _ => "bar" // MList[T].?:(1)とマッチする場合
    case _ => "baz"
  }
}