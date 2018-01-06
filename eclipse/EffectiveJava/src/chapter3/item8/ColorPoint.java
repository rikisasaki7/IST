package chapter3.item8;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicInteger;

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
 * このクラスは全てNGケース。OKケースは以下
 * @see /EffectiveJava/src/chapter3/item8/CompositionColorPoint
 */
public class ColorPoint extends Point {

	private Color color;
	/** デフォルトコンストラクタ */
	public ColorPoint(int x, int y, Color color){
		super(x, y);
		this.color = color;
	}

//	// 対称性を守っていないのでNG case1
//	@Override
//	public boolean equals(Object o){
//		if(!(o instanceof ColorPoint)) return false;
//		return super.equals(o) && ((ColorPoint) o).color == color;
//	}

//	// 推移性を守っていないのでNG case2
//	@Override
//	public boolean equals(Object o){
//		if(!(o instanceof Point)) return false;
//		// oが普通のポイントなら、色を無視した比較をする
//		if(!(o instanceof ColorPoint)) return o.equals(this);
//
//		// oは完全な比較を行う
//		return super.equals(o) && ((ColorPoint) o).color == color;
//	}

	/** mainメソッド */
	public static void main(String[] args){
//		// case1を検証 -> 一つ目はtrue、二つ目はfalseになる -> 対称性が守られていない
//		Point p = new Point(2, 3);
//		ColorPoint cp = new ColorPoint(2, 3, Color.RED);
//		System.out.println(p.equals(cp));
//		System.out.println(cp.equals(p));
//		// case2を検証 -> 一つ目はtrue、二つ目はtrue、３つ目はfalseになる -> 推移性が守られていない
//		ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
//		Point p2 = new Point(1, 2);
//		ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
//		System.out.println(p1.equals(p2));
//		System.out.println(p2.equals(p3));
//		System.out.println(p1.equals(p3));
		// case3を検証 -> staticイニシャライザ同様のPointインスタンスを渡すと、全てtrue
		// しかし、CounterPointを渡しても全てfalse -> リスコフの置換原則を破っている
		System.out.println(Point.onUnitCircle(new Point(1, 0)));
		System.out.println(Point.onUnitCircle(new Point(0, 1)));
		System.out.println(Point.onUnitCircle(new Point(-1, 0)));
		System.out.println(Point.onUnitCircle(new Point(0, -1)));
		System.out.println(Point.onUnitCircle(new CounterPoint(1, 0)));
		System.out.println(Point.onUnitCircle(new CounterPoint(0, 1)));
		System.out.println(Point.onUnitCircle(new CounterPoint(-1, 0)));
		System.out.println(Point.onUnitCircle(new CounterPoint(0, -1)));
	}

}

/**
 * case3がNGであることを検証するためのクラス
 * 既存のPointクラスに、生成されたインスタンスの個数を保持する拡張を施そうとしている。
 * @author 理貴
 */
class CounterPoint extends Point {
	private static final AtomicInteger counter = new AtomicInteger();
	/** デフォルトコンストラクタ */
	public CounterPoint(int x, int y){
		super(x, y);
		counter.incrementAndGet();
	}
	public int numberCreated() {
		return counter.get();
	}
}

