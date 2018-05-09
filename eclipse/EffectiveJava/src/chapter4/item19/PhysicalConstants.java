package chapter4.item19;

/*
 * 項目１９
 * 型を定義するためだけにインタフェースを使用する
 * 定数を提供するために使うものではない。
 * インタフェースをクラスが実装するとき、そのクラスのインスタンスでクライアントは何ができるかを述べているべき
 * 定数インタフェースパターンは使用しない。実装したクラスの内部構造を外部公開してしまうため
 * 定数が既存クラスに結びつくなら、そのクラスやインタフェースに定義すべき
 * 列挙型ならenumを使用する
 * そうでなければインスタンス化不可のユーティリティクラスを使用する
 *
 * 定数ユーティリティクラスの例
 */
public class PhysicalConstants {

	/* コンストラクタ privateにしてインスタンス化できないようにする */
	private PhysicalConstants(){}

	public static final double AVOGADROS_NUMBER = 6.02214199e23;
	public static final double BOLTZMANN_CONSTANT = 1.3806503e-23;
	public static final double ELECTRON_MASS = 9.109381883-31;

}
