package chapter4.item16;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/*
 * @author Riki
 * 項目１６
 * 継承よりコンポジションを選ぶ
 *
 * コンポジションの実装例クラス。
 * 既存クラスを拡張する代わりに、privateのフィールドで保持する。
 *
 * 保持しているクラスの各インスタンスメソッドは、保持している既存クラスのインスタンスに対して、
 * 対応するメソッドを呼び出して結果を返す「転送と呼ぶ」
 * 新たなクラスのメソッドは「転送メソッド」と呼ばれる
 *
 * 他のSetインスタンスをラップしているので、ラッパークラスと呼ばれる。
 *   ラッパークラス－継承の代わりにコンポジションを使用している。
 * @see /EffectiveJava/src/chapter4/item16/ForwardingSet.java
 *
 * また、クラスが計測を追加することでセットを装飾するので、デコレータパターンとも呼ばれる。
 *
 * ラッパークラスの欠点は、コールバックフレームワークで使用するのに向いていないこと。
 * ラップされているクラス（今回はSet）はラッパー（今回はAppropriateInstrumentedSet）を知らないので、
 * コールバックで呼び出される時の自分自身（Set）を返してしまうとの事。
 * SELF問題と言われるらしい：
 *   http://d.hatena.ne.jp/amachang/20100219/1266605361
 *
 * ***************************************************
 * 継承について：
 * サブクラスがスーパークラスのサブタイプである場合は、継承を検討する。
 * 言い換えれば、クラスBはクラスAとの間に「is-a」関係が存在している場合だけ、クラスＡを拡張するべき
 */
public class AppropriateInstrumentedSet<E> extends ForwardingSet<E> {

	private int addCount = 0;
	public AppropriateInstrumentedSet(Set<E> s){
		super(s);
	}

	@Override
	public boolean add(E e){
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c){
		addCount += c.size();
		return super.addAll(c);

	}

	public int getAddCount(){
		return addCount;
	}

	/*
	 * mainメソッド
	 */
	public static void main(String[] args){

		// 使ってみる
		Comparator<Date> cmp = null;
		Set<Date> s = new AppropriateInstrumentedSet<Date>(new TreeSet<Date>(cmp));
	}
}