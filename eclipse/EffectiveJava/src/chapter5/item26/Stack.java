package chapter5.item26;

import java.util.Arrays;
import java.util.EmptyStackException;

/*
 * 項目２６
 * ジェネリック型を使用する
 *
 * クラスをジェネリック化する最初のステップは、
 * その宣言に１つ以上の型パラメータを使用する事。その標準的名前はE。
 *
 * 項目６のStackをジェネリック化してみる
 * @see chapter2.item06.Stack
 */
public class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/** コンストラクタ */
	@SuppressWarnings("unchecked")
	public Stack(){
//		elements = new E[DEFAULT_INITIAL_CAPACITY]; // ←ジェネリック配列の生成は許可されていないので、コンパイルエラー
		                                            // メッセージ：E の総称配列を作成できません
		// ==============================================================
		// 回避策は２つある。
		// １：Object配列を生成して、ジェネリック配列型へキャストする
		// ２：フィールドelementsの型をE[]からObject[]へ変更する　※class：StackTwo<E>を参照
		//
		// ２つのどちらを選択するかは好みによるが、スカラー型への無検査キャストを抑制するよりは、配列型への無検査キャストを
		// 抑制する方が危険は少ないため、２の方が若干安全。
		// しかし、そうすると要素を取り出している個所全てにキャストを仕込む必要がある。
		// 一方、１は配列生成時の一カ所のみキャストをすればよいため、楽。そのため、１が使用されることが多いそう。
		// ==============================================================

		// １：単純にキャストすると無検査警告がでる
		// 今回の場合は、elementsはprivateで必ずE[]型になる、かつpushでE型の要素しか追加されないため、型安全と判断できる。
		// そのため、警告を抑制する。今回はコンストラクタ内で一行しか記述がないため、コンストラクタに対して
		// アノテーションを付与する
		// 以下、警告抑制の説明文
		// elements配列はpush(E)からのEインスタンスだけを含む。
		// そのことは、型安全性を保障するためには十分であるが、配列の実行時の型はE[]ではない。
		// 常にObjectである。
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

	/**
	 * メインメソッド
	 * ジェネリックStackを使用する小さなプログラム
	 */
	public static void main(String[] args){

		String[] tmp = {"aaa", "bbb", "ccc"};
		Stack<String> stack = new Stack<String>();
		for(String arg: tmp){
			stack.push(arg);
		}
		while(!stack.isEmpty()){
			System.out.println(stack.pop().toUpperCase());
		}
	}

	/** stackの中身が空かを返却する */
	private boolean isEmpty(){return size == 0;}
}

/*
 * ２：フィールドelementsの型をE[]からObject[]へ変更するのためのクラス
 */
class StackTwo<E> {

	private Object[] elements; // Objectにする
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/** コンストラクタ */
	public StackTwo(){
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(E e){
		ensureCapacity();
		elements[size++] = e;
	}

	public E pop(){
		if(size == 0){
			throw new EmptyStackException();
		}
//		E result = elements[--size]; // ←コンパイルエラーが発生する。E型はObjectと互換性がない。
		                             // メッセージ：型の不一致: Object から E には変換できません

		// pushは要素が型Eであることを要求しているので、キャストは正しい。
		@SuppressWarnings("unchecked")
		E result = (E) elements[--size]; // Eにキャストすることで、無検査キャスト警告にすることができる
		                                 // メッセージ：型の安全性: Object から E への未検査キャスト
		                                 // Eは具象化不可能型であり、実行時にはEはイレイズされるし、EはそもそもObjectとして扱われる。
		                                 // よって、ObjectのものをObjectに入れる事になるため、無検査キャストの安全性を示すことができる。警告を抑制する。
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
}
