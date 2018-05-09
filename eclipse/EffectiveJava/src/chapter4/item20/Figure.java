package chapter4.item20;

/*
 * 項目２０
 * タグ付クラスよりクラス階層を選ぶ
 *
 * タグ付きクラス：クラス階層よりかなり劣る
 */
class Figure {

	enum Shape{ RECTANGLE, CIRCLE };

	/* タグフィールド */
	final Shape shape;

	// shapeがCIRCLEである場合にだけこのフィールドは使われる
	double length;
	double width;

	// shapeがRECTANGLEである場合にだけこのフィールドは使われる
	double radius;

	/* 円のためのコンストラクタ */
	public Figure(double radius){
		shape = Shape.CIRCLE;
		this.radius = radius;
	}

	/* 四角のためのコンストラクタ */
	public Figure(double length, double width){
		shape = Shape.RECTANGLE;
		this.length = length;
		this.width = width;
	}

	/* 面積を求める */
	double area(){
		switch(shape){
		case RECTANGLE:
			return length * width;
		case CIRCLE:
			return Math.PI * (radius * radius);
		default:
			throw new AssertionError();
		}
	}


}
