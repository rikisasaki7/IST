package chapter4.item15;

/**
 * @author Riki
 * 項目１５
 * 可変性を最小限にする
 * classをfinalで定義し、サブクラスができない問題に対する代替案
 *
 * ・クラスをfinalにしない
 * ・コンストラクタを全てprivateもしくはパッケージプライベートとする
 * ・publicのコンストラクタの代わりにstaticファクトリメソッドをを追加する
 *
 * FactoryComplex：コンストラクタの代わりにstaticファクトリメソッドを持つ不変クラス
 * ※Complexクラスで作って見たのとほぼ同じ
 */
public class FactoryComplex {
	/* 実数部 */
	private final double re;
	/* 虚数部 */
	private final double im;

	/* アクセッサーとミューテータ */
	public double realPart(){return re;}
	public double imaginaryPart(){return im;}

	/*
	 * privateのコンストラクタ
	 */
	private FactoryComplex(double re, double im){
		this.re = re;
		this.im = im;
	}

	/*
	 * staticファクトリメソッド
	 */
	public static FactoryComplex valueOf(double re, double im){
		return new FactoryComplex(re, im);
	}

	/*
	 * 異なる用途でも使えるstaticファクトリメソッド。
	 * この場合は単なる複素数を生成するのではなく、座標軸に基づく複素数生成を行う。
	 * staticファクトリメソッドを使用すると、シグネチャが同一でもどんどこ作れるのが便利
	 */
	public static FactoryComplex valueOfPolar(double r, double theta){
		return new FactoryComplex(r * Math.cos(theta), r* Math.sin(theta));
	}
}
