package chapter4.item20;

/*
 * 項目２０
 * タグ付クラスよりクラス階層を選ぶ
 *
 * ①タグの値に振る舞いが依存している個々の操作に対する抽象メソッドを含んだ抽象クラスを定義
 * 　※抽象クラスは階層のルートとなる
 * ②タグの値に依存しない振る舞いを持つメソッドやフィールドがあるならば、抽象クラスで定義する
 * ③個々の特性に対して具象サブクラスを定義し、実装をサブクラスに記述する
 * タグ付きクラスに対するクラス階層による置換
 *
 * ①の抽象クラス定義
 */
public abstract class AbstractFigure {

	// ①抽象メソッド
	abstract double area();
	// ②タグに依存しないor全て使用すると仮定したフィールドと操作
	int hash = (int) Math.random();
	int getHash(){
		return hash;
	}
}

/* ③具象サブクラス - 円 */
class Circle extends AbstractFigure {
	final double radius;
	/* コンストラクタ */
	Circle(double radius){
		this.radius = radius;
	}
	/* 実装を記述 */
	double area(){
		return Math.PI * (radius * radius);
	}
}

/* ③具象サブクラス - 四角 */
class Rectangle extends AbstractFigure {
	final double length;
	final double width;
	/* コンストラクタ */
	Rectangle(double length, double width){
		this.length = length;
		this.width = width;
	}
	/* 実装を記述 */
	double area(){
		return length * width;
	}
}

/*
 * 更に拡張可能
 * ③具象サブクラス - 正方形
 */
class Square extends Rectangle {
	Square(double side){
		super(side, side);
	}
}