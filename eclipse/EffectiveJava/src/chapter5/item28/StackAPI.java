package chapter5.item28;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

/*
 * 項目２８
 * APIの柔軟性向上のために境界型ワイルドカードを使用する
 */
public class StackAPI<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	// ===========================================================
	// Stackクラスのコピー
	// ===========================================================
	public StackAPI(){
		elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
	}
	public void push(E e){
		ensureCapacity();
		elements[size++] = e;
	}
	public E pop(){
		if(size == 0){
			throw new EmptyStackException();
		}
		E result = elements[--size];
		elements[size] = null;
		return result;
	}

	/**
	 * 配列を大きくする必要がある毎に容量をだいたい倍にして
	 * 最低もう１つの要素分の容量を確保する
	 */
	private void ensureCapacity() {
		if(elements.length == size){
			elements = Arrays.copyOf(elements, 2*size+1);
		}
	}

	/** stackの中身が空かを返却する */
	private boolean isEmpty(){return size == 0;}

	/** メインメソッド */
	public static void main(String[] args){
		StackAPI<Number> numberStack = new StackAPI<Number>();
		Iterable<Integer> integers = new Iterable<Integer>() {
			private List<Integer> iList = new ArrayList<Integer>();
			private void add(Integer i){ iList.add(i); }
			@Override
			public Iterator<Integer> iterator() {
				// TODO 自動生成されたメソッド・スタブ
				add(1);
				add(2);
				add(3);
				return iList.iterator();
			}
		};

		// =======================
		// pushAllの検証
		// =======================
//		numberStack.pushAll(integers);
		// コンパイルエラーが発生
		// メッセージ：型 StackAPI<Number> のメソッド pushAll(Iterable<Number>) は引数 (Iterable<Integer>) に適用できません
		// Stack<Integer>とStack<Number>は親子関係ではないから。

		numberStack.pushAllGenerics(integers);
		// 境界型ワイルドカードを使用するとコンパイルエラーとならない

		// =======================
		// popAllの検証
		// =======================
		Collection<Object> objects = new ArrayList<Object>();
//		numberStack.popAll(objects);
		// コンパイルエラー
		// メッセージ：型 StackAPI<Number> のメソッド popAll(Collection<Number>) は引数 (Collection<Object>) に適用できません
		// Collection<Object>とCollection<Object>は親子関係ではないから。

		numberStack.popAllGenerics(objects);
		// 境界型ワイルドカードを使用するとコンパイルエラーとならない

		// =======================
		// reduceの検証
		// =======================
		StackAPI<String> s  = new StackAPI<String>();
		List<String> sList = new ArrayList<String>();
		sList.add("aaaaa");
		sList.add("bbbbb");
		String retS = StackAPI.reduce(sList, s.new FunctionGString(), "ahah! ");
		System.out.println(String.format("reduceG.result: %s", retS));
	}

	// ===========================================================
	// StackAPIクラス独自のメンバ
	// ===========================================================
	/*
	 * ワイルドカードを使用しないpushAllメソッド - 不十分
	 * 一連の要素を受け取り、それらをすべてスタックにプッシュする
	 */
	public void pushAll(Iterable<E> src){
		for(E e : src){
			push(e);
		}
	}

	/*
	 * 境界型ワイルドカードを使用したpushAll
	 * 全ての型は自分自身のサブタイプであるため、Eを引数に指定しても問題ない。
	 *
	 * Eプロデューサー（生産者）としての役割のパラメーターのためのワイルドカード型
	 */
	public void pushAllGenerics(Iterable<? extends E> src){
		for(E e : src){
			push(e);
		}
	}

	/*
	 * ワイルドカード型を使用しないpopAllメソッド - 不十分
	 */
	public void popAll(Collection<E> dst){
		while(!isEmpty()){
			dst.add(pop());
		}
	}

	/*
	 * 境界型ワイルドカードを使用したpopAll
	 * 全ての型は自分自身のスーパータイプであるため、Eを引数にしても問題ない。
	 *
	 * Eコンシューマー（消費者）としての役割のパラメータのためのワイルドカード型
	 */
	public void popAllGenerics(Collection<? super E> dst){
		while(!isEmpty()){
			dst.add(pop());
		}
	}

	// =======================================================================
	// 境界型ワイルドカードの考え方
	// プロデューサーかコンシューマをあらわす入力パラメータに対してワイルドカードを使用する。
	// パラメータがプロデューサーかつコンシューマの場合は不要。正確な型一致が必要でワイルドカードを使用する意味がない。
	//
	// PCESは、プロデューサー（producer）-extends、コンシューマー（consumer）-superを表す
	//
	// pushAllの引数は、Stackが使用するEインスタンスを生産するのでextendsを使用する
	// 　⇒Stackに入れるものがE。入れるものはEの子クラスでも入れたい。（親クラスは入れない）だから? extends E
	// popAllの引数は、StackからのEインスタンスを消費するのでsuperを使用する
	// 　⇒Stackから出して渡された値を入れるCollectionがE。Collectionに入れるものはCollectionのジェネリック型の子クラスが良い。だから? super E
	// =======================================================================
	/*
	 * 項目２５のreduceメソッドを境界型ワイルドカードで実装
	 * listは値を消費することも生産することもできるが、listはEプロデューサーとして使用されている。
	 * 　⇒listを使用して新しいList<E>型に入れている。だからlistは、Eの子クラスである必要がある。
	 *
	 * Eプロデューサーとしての役割のパラメータのためのワイルドカード型
	 */
	static <E> E reduce(List<? extends E> list, FunctionG<E> f, E initVal){
		List<E> snapshot;
		synchronized(list){
			snapshot = new ArrayList<E>(list);
		}
		E result = initVal;
		for(E e: snapshot){
			result = f.apply(result, e);
		}
		return result;
	}
	/** ジェネリクスを使用 */
	interface FunctionG<T>{
		T apply(T arg1, T arg2);
	}
	/** ジェネリクスを使用したインタフェースの実装クラス */
	class FunctionGString implements FunctionG<String>{
		@Override
		public String apply(String arg1, String arg2){
			return arg1 + arg2;
		}
	}
}
