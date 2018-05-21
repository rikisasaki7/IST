package chapter4.item22;

import java.util.AbstractSet;
import java.util.Iterator;

/*
 * ネストしたクラス
 * 　・staticのメンバークラス
 * 　　　⇒外部クラスと一緒に使用すると有用なpublicのヘルパークラスとして使用
 * 　　　　例えば外部クラスに密接するenum
 * 　・非staticのメンバークラス
 * 　　　⇒外部クラスのエンクロージングインスタンスと暗黙に関連付けされる
 * 　　　　エンクロージングインスタンスに対してメソッド呼び出し可能
 * 　　　　エンクロージングインスタンスの参照を就職されたthis構文を使用して得る事が可能
 * 　　　　　⇒外部クラスと分離できるならstaticのメンバークラスにするべき
 * 　　　⇒エンクロージングインスタンスの生成時に関連付けが確立する。
 * 　　　⇒メモリの使用量増加に繋がる
 * 　　　　　⇒一般的な使い方は、エンクロージングインスタンスを関係のないクラスの
 * 　　　　　　インスタンスとしてみなすことを可能とするアダプターを定義する事。
 * 　・無名クラス
 * 　　　⇒エンクロージングクラスのメンバーではない。使用される時点でインスタンス化される
 * 　　　⇒式が許されている個所ではどこでも記述可能
 * 　　　⇒短くあるべき、１０行以内とか
 * 　　　⇒一般的な使用方法は３つ
 * 　　　　　＊関数オブジェクトを生成する事
 * 　　　　　＊Runnable、TimerTask、Thread等のプロセスオブジェクトを生成する事
 * 　　　　　＊staticファクトリーメソッド内での使用
 * 　・ローカルクラス
 *
 * 　・要約：
 * 　　　＊１つのメソッドの外からも見える必要があったり、メソッド内に問題なく入れるのに長すぎる⇒メンバークラス
 * 　　　＊個々のインスタンスがエンクロージングインスタンスへの参照が必要ならば非staticにする
 * 　　　＊そうでなければstaticにする
 * 　　　＊クラスがメソッド内に属しているべきであり、１カ所からのみインスタンスを生成する必要があり、
 * 　　　　そのクラスを特徴づける型が既に存在していれば無名クラス
 * 　　　＊そうでなければローカルクラス
 *
 * 非staticのメンバークラスの典型的使用方法
 */
public class MySet<E> extends AbstractSet<E> {
	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	@Override
	public int size() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	// 非staticのメンバークラス
	private class MyIterator implements Iterator<E>{

		@Override
		public boolean hasNext() {
			// TODO 自動生成されたメソッド・スタブ
			return false;
		}

		@Override
		public E next() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}

}
