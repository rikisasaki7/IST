package chapter4.item16;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Riki
 * 項目１６
 * 継承よりコンポジションを選ぶ
 * 継承はカプセル化を破る。サブクラスはスーパークラスの実装に依存するため、
 * スーパークラスの実装によってはサブクラスが壊れる。
 * その例がこのクラス
 * 不完全－継承の不適切な使用！
 * HashSetが生成されてから要素が追加された回数を返却するクラス
 */
public class InappropriateInstrumentedHashSet<E> extends HashSet<E> {

	/* 要素の挿入回数 */
	private int addCount = 0;

	/* デフォルトコンストラクタ */
	public InappropriateInstrumentedHashSet(){}

	/* コンストラクタ */
	public InappropriateInstrumentedHashSet(int initCap, float loadFactor){
		super(initCap, loadFactor);
	}

	/*
	 * HashSetのaddメソッドをオーバーライド
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e){
		addCount++;
		return super.add(e);
	}

	/*
	 * (非 Javadoc)
	 * @see java.util.AbstractCollection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c){
		addCount += c.size();
		return super.addAll(c);
	}


	/*
	 * アクセッサー
	 */
	public int getAddCount(){
		return addCount;
	}

	/*
	 * mainメソッド
	 */
	public static void main(String[] args){

		// 上記クラスだとうまくいかない
		InappropriateInstrumentedHashSet<String> s = new InappropriateInstrumentedHashSet<String>();
		s.addAll(Arrays.asList("Snap", "Crackle", "pop"));
		// ３つの要素を追加したのに、６と出る
		// それは、HashSetのaddAllは内部でaddメソッドを使用しており、addが３回呼び出された後、addAllの+=が実行されるから
		System.out.println(String.format("addCount: %s", s.getAddCount()));

		// *********************************************************
		// addAllのオーバーライドを取り除けば動くが、結局super.addAllをreturnしているので、HashSetクラスが内部的にaddを使用している事に依存する
		// addAllの中で、collectionだけイテレートしても良いが、自己利用（superクラスのメソッドを呼び出して、自クラスのメソッドを使用する？）
		//   があるかもしれないし、していないかもしれないスーパークラスのメソッドを再実装するのに等しく、難しい（？？）
		// また、スーパークラスの実装によってはエラーになる可能性がある。
		// また、サブクラスを作る時点でスーパークラスとは異なるシグネチャのメソッドを作っても、スーパークラスで運悪く同じシグネチャの
		//   メソッドが作られたら、自動的にオーバーライドされてしまう。
		//
		// ⇒上記を解決する方法が、「コンポジション」（もう一個のクラスを参照）
		// *********************************************************
	}

}
