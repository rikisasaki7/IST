package chapter5

/**
 * オブジェクト志向プログラミングに基づいた図形ライブラリ
 * 図形を抽象化したトレイト
 * 各クラスの振る舞いを表現するには優れているが、機能追加に弱い
 * 特にこのケースでは、フィールドをアクセサメソッドで公開しなければいけない
 */
trait OShape {
  def area: Double
}

/** 円 */
class OCircle(radius: Double) extends OShape {
  /** 面積を計算する */
  def area = radius * radius * Math.PI
  /** 半径を返却する */
  def getRadius = radius
}

/** 正方形 */
class OSquare(length: Double) extends OShape {
  /** 面積を計算する */
  def area = length * length
  /** １辺の長さを返却する */
  def getLength = length
}

/** 長方形 */
class ORectangle(h: Double, w: Double) extends OShape {
  /** 面積を計算する */
  def area = h * w
  /** 縦を返却する */
  def getHeight = h
  /** 横の長さを返却する */
  def getWidth = w
}

/**
 * パターンマッチを使用した図形ライブラリ
 * 図形を抽象化したトレイト
 * 各図形のcaseクラスではareaメソッドがないので、何をするクラスかわかりづらいが、
 * 機能追加にには強い（perimeterメソッドを後から追加した）
 */
trait Shape
/** 円を表すケースクラス */
case class Circle(radiul: Double) extends Shape
/** 正方形を表すケースクラス */
case class Square(length: Double) extends Shape
/** 長方形を表すケースクラス */
case class Rectangle(h: Double, w: Double) extends Shape
object Shape {
  /**
   * 面積を計算する関数
   * パターンマッチを使用して使用するロジックを分けている
   * @param shape 図形
   * @return 計算した面積
   */
  def area(shape: Shape): Double = shape match {
    case Circle(r) => r * r * Math.PI
    case Square(l) => l * l
    case Rectangle(h, w) => h * w
  }
  
  /**
   * 円周、辺の長さの合計を計算する関数
   * @param shape 図形
   * @return 計算した円周、辺の長さの合計
   */
  def perimeter(shape: Shape) = shape match {
    case Circle(r) => 2 * Math.PI * r
    case Square(l) => 4 * l
    case Rectangle(h, w) => h * 2 + w * 2
  }
}