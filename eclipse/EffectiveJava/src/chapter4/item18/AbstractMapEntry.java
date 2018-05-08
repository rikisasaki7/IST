package chapter4.item18;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * 項目１８
 * 抽象クラスよりインタフェースを選ぶ
 * 抽象クラスとインタフェースの違い：
 * 　・抽象クラス：
 * 　　　メソッド実装を許されている
 * 　　　実装するにはサブクラスでなければいけない
 * 　　　★既存クラスに新たな抽象クラスを実装させることはほぼ無理★
 * 　　　
 * 　・インタフェース：
 * 　　　メソッド実装を許されていない
 * 　　　階層の制限がない。サブクラスでなくてもよい
 * 　　　★既存クラスに新たなインタフェースを実装させることが容易★
 * 　　　ミックスインを定義できる
 * 　　　ミックスインを定義した例：SingerSongwriter.java
 *
 * インタフェースに対する抽象骨格実装クラスを提供することで、抽象クラスとインタフェースの長所を組み合わせる事ができる
 * インタフェースが型を定義し、骨格実装クラスが実装を行う関係。
 * 慣例として骨格実装は、AbstractInterfaceという命名がされる。
 *
 * 疑似多重継承：
 * 　インタフェースを実装しているクラスが、骨格実装を拡張したprivateのインスタンスを保持しておき、
 * 　インタフェースの呼び出しをそのインスタンスへ転送できる。
 * 骨格実装の記述ガイドライン：
 * 　・インタフェースを調べて他のメソッドを実装できる基本操作がどのメソッドかを決める
 * 　　⇒全て骨格実装では抽象メソッドとなる。（クラスごとに異なる処理はこっち）
 * 　・インタフェースの他のメソッド全ての具体的な実装を提供する。（どのクラスでも共通の処理はこっち）
 *
 * 抽象クラスの利点
 * 　・発展が容易（インタフェースはリリースされると変更できない）
 *
 * まとめ
 * 　・インタフェースは注意深く設計し、良くテストしてからリリースする事
 * 　・インタフェースは複数の実装を許す型を定義する最善の方法である
 * 　・発展のしやすさが、柔軟性と機能性より重要視される場合は抽象クラスを使用する
 * 　・その場合も、抽象クラスの限界を理解した上で使用する
 * 　・インタフェースを提供するなら付随する骨格実装を検討する
 *
 * 以下骨格実装のサンプル（一部他のサンプル含む）
 */
public abstract class AbstractMapEntry<K, V> implements Map.Entry<K,V> {

	/*
	 * 骨格実装上に構築された具象実装を含むstaticファクトリーメソッド
	 * int配列をIntegerインスタンスのリストとして見せるアダプターとなる。
	 * また、staticファクトリメソッド内に隠蔽されている無名クラスとなる。（この例は）
	 */
	static List<Integer> intArrayAsList(final int[] a){
		if(a == null)
			throw new NullPointerException();
		return new AbstractList<Integer>(){
			public Integer get(int i){
				return a[i]; // 自動ボクシング
			}
			@Override
			public Integer set(int i, Integer val){
				int oldVal = a[i];
				a[i] = val; // 自動アンボクシング
				return oldVal; // 自動ボクシング
			}
			public int size(){
				return a.length;
			}
		};
	}

	/*
	 * 疑似多重継承を試してみた。
	 * privateでもつAbstractListを拡張したArrayList
	 */
	private ArrayList<String> a;

	/*
	 * 疑似多重継承を試してみた。
	 * AbstractListのindexOfを呼び出すメソッド
	 */
	public int abstractListIndesOf(Object o){
		return a.indexOf(o);
	}

	// *************************************************
	//
	// 骨格実装のサンプル
	//
	// *************************************************

	/* 基本操作 */
	public abstract K getKey();
	public abstract V getValue();

	/* 変更可能なマップでのエントリーは、このメソッドをオーバーライドしなければならない */
	public V setValue(V value){
		throw new UnsupportedOperationException();
	}

	/*
	 * Map.Entry.equalsの一般契約を実装
	 */
	@Override
	public boolean equals(Object o){
		if(o == this)
			return true;
		if(!(o instanceof Map.Entry))
			return false;
		Map.Entry<?, ?> arg = (Entry<?, ?>) o;
		return equals(getKey(), arg.getKey()) && equals(getValue(), arg.getValue());

	}

	private static boolean equals(Object o1, Object o2){
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/*
	 * Map.Entry.hashCodeの一般契約を実装
	 */
	@Override
	public int hashCode(){
		return hashCode(getKey())^hashCode(getValue());
	}

	private static int hashCode(Object obj){
		return obj == null ? 0 : obj.hashCode();
	}

	/* mainメソッド */
	public static void main(String[] args){
		SimpleMap<String, Integer> s = new SimpleMap<String, Integer>();
		s.getKey();
		s.getValue();
		s.hashCode();

	}
}

@SuppressWarnings("hiding")
class SimpleMap<String, Integer> extends AbstractMapEntry<String, Integer>{

	@Override
	public String getKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Integer getValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}