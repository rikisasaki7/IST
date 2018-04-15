package chapter4.item15;

/**
 * @author Riki
 * 項目１５
 * 可変性を最小限にする
 * ◆クラスを不変にするための５つの規則
 * １.オブジェクトの状態を変更するいかならるメソッドも提供しない
 * ２.クラスが拡張できない事を保証する。
 * ３.すべてのフィールドをfinalにする。
 * ４.すべてのフィールドをprivateにする。
 * ５.可変コンポーネントに対する独占的アクセスを保証する。
 * 　　ー＞可変オブジェクトへの参照をもつフィールドがあるなら、それをクライアントが使用できてはけない。
 * 
 * ◆不変オブジェクトの共有
 * 不変オブジェクトは値が変わらないから、スレッドセーフとなる。同期は不要。限りなく共有できる。
 * また、cloneやコピー（変換）コンストラクタ・ファクトリも不要。
 * 
 * ◆不変オブジェクトのメリットたち
 * １.不変オブジェクトを共有できるだけなく、その内部を共有できる
 * 　　ー＞内部も不変なのだから、cloneのときのように配列をdeepCopyする必要がない。
 * 　　　　結局参照先も不変だから、同じ参照で問題ない。
 * ２.不変オブジェクトは保管オブジェクトに対する素晴らしい建築ブロックを作り出します。
 * 　　ー＞不変式について気にする必要がなくなるという意味。
 * 　　　　mapやsetに格納されたオブジェクトたちが、変化を起こして不変式を壊すような事を気にしなくてよい
 * 
 * ◆不変オブジェクトの欠点
 * １.個々の異なる値に対して別々のオブジェクトを必要とする事
 * 　　ー＞特にオブジェクトが大きい場合には顕著（１００のフィールドがあって、１つのフィールドだけ異なるオブジェクトを生成したい場合など）
 * 解決策１.操作が想定されるなら、その操作を一括で解決してくれるメソッドを用意する
 * 解決策２.publicの可変コンパニオンクラスを用意する。（Stringでいう、StringBuilder）
 * 
 * ◆制限を緩めることについて
 * さらにいうと、実際のところ全てを不変にするのは難しかったりする。
 * その場合には、メソッドがオブジェクトの状態に関して、外部にわかる変更をしないように気をつけながら、制限を緩めていく。
 * また、計算にコストがかかるフィールドがあり、かつ頻繁に使われるわけではないならば、遅延初期化を検討しても良い。
 * 
 * Complexクラスは、複素数を表しています。（サンプル的に）
 */
public final class Complex {

	/* 実数部分 */
	private final double re;
	/* 虚数部分 */
	private final double im;
	
//	/*
//	 * コンストラクタ 
//	 * インスタンスは別に作れて良い。その後不変にできれば良い
//	 */
//	public Complex(double re, double im){
//		this.re = re;
//		this.im = im;
//	}

	/*
	 * コンストラクタ 
	 * 共有の考え方を進めて、privateにしてstaticファクトリメソッドを提供しても良い
	 * @see this.newInstance
	 */
	private Complex(double re, double im){
		this.re = re;
		this.im = im;
	}
	
	/*
	 * 対応するミューテーターを持たないアクセッサー（いわゆるgetメソッド）
	 * 加えて４つの算術操作である加減乗除を提供。
	 * それぞれの中でフィールドの値を書き換えているのではなく、新しいインスタンスをreturnしている。
	 * ー＞関数的方法である！どん
	 * ー＞オペランドの状態を変えるようなものは手続き型or命令型と呼ばれる。
	 */
	public double realPart(){return re;}
	public double imaginaryPart(){return im;}

	public Complex add(Complex c){
		return new Complex(re + c.re, im + c.im);
	}
	public Complex subtract(Complex c){
		return new Complex(re - c.re, im - c.im);
	}
	public Complex multiply(Complex c){
		return new Complex(re * c.re - im * c.im, re * c.re + im + c.im);
	}
	public Complex divide(Complex c){
		double tmp = c.re * c.re + c.im + c.im;
		return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
	}
	
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(!(o instanceof Complex))
			return false;
		Complex c = (Complex) o;
		// DoubleとFloatは特別な対応が必要。
		return Double.compare(re, c.re) == 0 && Double.compare(im, c.im) == 0;
	}
	
	@Override
	public int hashCode(){
		int result = 17 + hashDouble(re);
		result = 31 * result + hashDouble(im);
		return result;
	}
	
	/*
	 * doubleの値を受付、intのハッシュ値に変換して返却します。
	 * @param val ハッシュ化したいdoubleの値
	 * @return ハッシュコード
	 */
	private static int hashDouble(double val){
		long longBits = Double.doubleToLongBits(val);
		return (int) (longBits - (longBits >>> 32));
	}
	
	@Override
	public String toString(){
		return "(" + re + " + " + im + "i)";
	}
	
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex I = new Complex(0, 1);
	
	/*
	 * 複素数クラスのstaticファクトリメソッド。
	 * 再利用できるものは再利用してる。
	 * 再利用できない場合に、privateのコンストラクタを呼び出してnewしたインスタンスを返却している
	 */
	public static Complex valueOf(int re, int im){
		if(re == 0 && im == 0) return ZERO;
		if(re == 1 && im == 0) return ONE;
		if(re == 0 && im == 1) return I;
		return new Complex(re, im);
	}
}
