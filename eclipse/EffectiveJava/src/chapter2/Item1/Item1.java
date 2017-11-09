/**
 * 
 */
package chapter2.Item1;
import java.util.Collections;
import java.util.List;

/**
 * @author Riki
 * 項目１
 * コンストラクタの代わりにstaticファクトリーメソッドを検討する
 */
public class Item1 {
	
	/** 真偽値 */
	private Boolean trueOrFalse;
	/** 真偽値がTrueのインスタンスを使用したいときに使う */
	private static Item1 trueItem1 = new Item1(true);
	/** 真偽値がFalseのインスタンスを使用したいときに使う */
	private static Item1 falseItem1 = new Item1(false);

	/** 
	 * @param trueOrFals 内包させる真偽値
	 * コンストラクタ
	 * privateにすることで、外部から使用できなくする 
	 */
	private Item1(Boolean trueOrFalse){
		this.trueOrFalse = trueOrFalse;
	}
	
	/** 
	 * 指定した真偽値を内容するItem1を返します
	 * @param b 内容させる真偽値
	 * @return 指定した真偽値を内容するItem1
	 */
	public static Item1 getTrueOrFalseItem1(boolean b){
		// コンストラクタをprivateにしたからできないけど、
		// ここでItem1のサブクラスをreturnしても問題ない
		return b ? trueItem1:falseItem1;
	}

	/**
	 * @param b ボクシング対象の真偽値
	 * @return 引数に渡された値のラッパークラス
	 * staticファクトリーメソッドの具体例
	 * 引数のbを元に、ラッパークラスを返す
	 * 通常のコンストラクトとは異なるstaticファクトリメソッドのメリット
	 * １：好きな名前をつけることができ、
	 * ２：用途ごとに複数作ることができる。
	 * @see 参考１
	 */
	public static Boolean valueOf(boolean b){
		// コンストラクタとは異なり、オブジェクトを生成する必要がない。
		// 同値のオブジェクトが頻繁に呼ばれるような時に負荷軽減を見込める
		// @see 参考２
		// 何度呼び出されても同じオブジェクトを返却するクラスを「インスタンス制御されている」と言う
		// インスタンス制御されているクラスの特徴
		// １：クラスがシングルトン、あるいはインスタンス化不可能であることを保証できる
		// ２：不変なクラスに２つの同じインスタンスが存在しないことを保証できる
		// →a==bの場合、a.equals(b)が成り立つ
		// →equalsメソッドの代わりに==を使用でき、パフォーマンス向上を見込める
		return b ? Boolean.TRUE: Boolean.FALSE;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Trueを内容するItem1を取得
		Item1 trueItem1 = Item1.getTrueOrFalseItem1(true);
		Item1 falseItem1 = Item1.getTrueOrFalseItem1(false);
		
		// インスタンス制御されたクラスの例である、Collectionsクラス
		// インスタンス化されないただ一つのCollectionsクラスから、SingletonListが返却される
		// 中を追って見ると、確かにSingletonListはpublicではなく、privateであり、実装クラスを隠蔽できている
		List<Item1> list = Collections.singletonList(trueItem1);
		System.out.println(list);
		System.out.println(list.contains(falseItem1));
		
		// せっかくだからService関連を使って見る
		// デフォルトプロバイダーを登録
		Services.registerDefaultProvider();
		// プロバイダーを作成
		TestProvider tp = new TestProvider();
		// プロバイダー登録APIでプロバイダーを登録
		Services.registerProvider("extend", tp);
		// サービスアクセスAPIでサービスを使用　その１
		Service s = Services.newInstance();
		System.out.println(s.writeString("yes"));
		
		// サービスアクセスAPIでサービスを使用　その２
		Service ds = Services.getInstance("extend", Services.DEFAULT_PROVIDER_SOMESTRING);
		System.out.println(ds.writeString("sasakisasaki!!"));

	}

	/**
	 * 参考１
	 * staticファクトリメソッドはデザインパターンのファクトリメソッドとは異なる
	 * ファクトリメソッド
	 */
	
	/**
	 * 参考２
	 * Fly weightパターンに似ている。
	 * 
	 */

	/**
	 * @return 真偽値
	 */
	public boolean isTrueOrFalse() {
		return trueOrFalse;
	}

	/**
	 * @param trueOrFalse 真偽値
	 */
	public void setTrueOrFalse(Boolean trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}
}

