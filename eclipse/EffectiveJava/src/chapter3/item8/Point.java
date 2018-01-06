package chapter3.item8;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Riki
 * 項目８
 * equalsをオーバーライドするときは一般契約に従う
 * 一般契約：
 * １．反射的
 * ２．対称的
 * ３．推移的　＜－このクラスではこれ
 * ４．整合的　＜－
 * ５．非Null性
 */
public class Point {
	private final int x;
	private final int y;
	/** デフォルトコンストラクタ */
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

//	@Override
//	public boolean equals(Object o){
//		if(!(o instanceof Point)) return false;
//		Point p = (Point) o;
//		return p.x == x && p.y == y;
//	}


	/**
	 * リスコフの置換原則を破っているのでNG case3
	 * ・リスコフの置換原則：
	 * 　　ある型のすべての重要な属性はそのサブタイプでも維持される。
	 * 　　従ってその型に対して書かれたすべてのメソッドはそのサブタイプでも同様に機能するべき
	 * なんとなくいじってみたけど参考書の通りにならんかった
	 * 具体的にはこのequalsメソッドをcontainsで呼び出してくれなかった。
	 * 参考書ではさもここが呼ばれるみたいに書いてあった
	 * だからonUnitCircleをちょっといじった
	 */
	@Override
	public boolean equals(Object o){
		if(o == null || o.getClass() != getClass()) return false;
		Point p = (Point) o;
		return p.x == x && p.y == y;
	}

	/**
	 * 単位円上の全てのPointインスタンスを含むようにUnitCircleを初期化する
	 */
	private static final Set<Point> unitCircle;
	static {
		unitCircle = new HashSet<Point>();
		unitCircle.add(new Point(1, 0));
		unitCircle.add(new Point(0, 1));
		unitCircle.add(new Point(-1, 0));
		unitCircle.add(new Point(0, -1));
	}

	/**
	 * 渡されたポイントが単位円上に存在するか確認をする。
	 * unitCircleはHashSetを用いている。コレクションのcontainsは内部的にequalsを使用しており、
	 * どのようなPointインスタンスを渡したとしても正しく値を取得することはできない
	 * ※参考書ではコメントアウトされているように記述されていたけど、動かないからなおした
	 * @param p Point
	 * @return 単位円上にPointが存在するか
	 */
	public static boolean onUnitCircle(Point p){
//		return unitCircle.contains(p);
		for(Point up: unitCircle){
			if(up.equals(p)) return true;
		}
		return false;
	}

}