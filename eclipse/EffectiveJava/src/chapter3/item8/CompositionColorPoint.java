package chapter3.item8;

import java.awt.Color;

/**
 * @author Riki
 * 項目８
 * equalsをオーバーライドするときは一般契約に従う
 * 一般契約：
 * １．反射的
 * ２．対称的
 * ３．推移的　＜－このクラスではこれ
 * ４．整合的
 * ５．非Null性
 * 既存クラスに新規に値要素（色）を追加するケースを考える
 * equals契約を破ることなく、値要素を追加する方法
 * 既存のPointを拡張するのではなく、コンポジションで値要素を追加し、
 * 同じ位置のポイントを返すビューメソッドを定義する
 * @see /EffectiveJava/src/chapter3/item8/CompositionColorPoint
 */
public class CompositionColorPoint {
	private final Point point;
	private final Color color;
	/** デフォルトコンストラクタ */
	public CompositionColorPoint(int x, int y, Color color){
		if(color == null) throw new NullPointerException();
		point = new Point(x, y);
		this.color = color;
	}

	/**
	 * このカラーポイントのポイントとしてのビューを返す
	 */
	public Point asPoint() {
		return point;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CompositionColorPoint)) return false;
		CompositionColorPoint cp = (CompositionColorPoint) o;
		return cp.point.equals(point) && cp.color.equals(color);
	}

	/** mainメソッド */
	public static void main(String[] args){

		// case1を検証 -> 全部falseになる
		Point p = new Point(2, 3);
		CompositionColorPoint cp = new CompositionColorPoint(2, 3, Color.RED);
		System.out.println(p.equals(cp));
		System.out.println(cp.equals(p));
		// case2を検証 ->  全部falseになる
		CompositionColorPoint p1 = new CompositionColorPoint(1, 2, Color.RED);
		Point p2 = new Point(1, 2);
		CompositionColorPoint p3 = new CompositionColorPoint(1, 2, Color.BLUE);
		System.out.println(p1.equals(p2));
		System.out.println(p2.equals(p3));
		System.out.println(p1.equals(p3));
		// その他検証
		CompositionColorPoint p4 = new CompositionColorPoint(1, 2, Color.BLUE);
		System.out.println(p3.equals(p4));
		// ビューメソッド
		System.out.println(p2.equals(p4.asPoint()));


	}
}
