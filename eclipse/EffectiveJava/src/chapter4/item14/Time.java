package chapter4.item14;

/**
 * @author Riki
 * 項目１４
 * publicのクラスでは、publicのフィールドではなくアクセッサーメソッドを使う
 *
 * Timeクラスは、フィールドをpublicで公開する際の、害が少ない方法。
 * 不変フィールドならまだ。。不変式を強制できる。疑わしくはあるが。
 */
public final class Time {
	private static final int HOURS_PER_DAY = 24;
	private static final int MINUTES_PER_HOUR = 60;
	
	public final int hour;
	public final int minute;
	
	/*
	 * コンストラクタ。
	 * 初期化時に値の妥当性をチェックしている
	 */
	public Time(int hour, int minute){
		if(hour < 0 || hour >= HOURS_PER_DAY)
			throw new IllegalArgumentException("Hour: " + hour);
		if(minute < 0 || minute >= MINUTES_PER_HOUR)
			throw new IllegalArgumentException("Min: " + minute);
		this.hour = hour;
		this.minute = minute;
	}
}

/*
 * インスタンスフィールドをまとめる事を目的としたクラス
 * このようなクラスはpublicにするべきではない。
 * 内部データにアクセスできてしまうと、その値の妥当性を行えない
 */
class Point{
	// これらのフィールドはクライアントから直接アクセスされてしまう。
	// リリースしたら永遠に変更することができなくなる
	public float x;
	public float y;
}

/*
 * アクセッサーメソッドとミューテータでカプセル化された構造体クラス
 * クラス内部の表現形式を変更する柔軟性を保つため、クラスがそのパッケージ外からアクセス可能ならば、
 * アクセッサーメソッドを提供する。
 */
class AccesserPoint{
	private double x;
	private double y;
	
	/* コンストラクタ */
	public AccesserPoint(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/* アクセッサーとミューテータ */
	public double getX(){return x;}
	public double getY(){return y;}
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
}

