package chapter2.item6;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author Riki
 * 項目６
 * 廃れたオブジェクト参照を取り除く
 * メモリリークが潜んでいるプログラム
 * 
 * 結論：メモリリークの原因は３つ
 * １：廃れた参照オブジェクト
 * ２：キャッシュ（参考PGなし）
 * ３：コールバック関数（参考PGなし）
 */
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	/** コンストラクタ */
	public Stack() {
		this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}
	public Object pop() {
		if(size == 0){
			throw new EmptyStackException();
		}
		// 廃れた参照を保持したまま。
		// 一度スタックを拡大した場合、スタックを小さくしてもスタックから取り出されたオブジェクトは
		// GCの対象とならない。
		// →このオブジェクトだけでなく、このオブジェクトが参照しているオブジェクトもその先もGCされないため、
		// 非常に大きなメモリを食う可能性がある。
//		return elements[--size]; // これは望ましくないコード。
		// 以下が望ましいコード
		Object result = elements[--size];
		elements[size] = null; // 廃れた参照を取り除く→GC対象とするとともに、アクセスするとNullPointerが発生し、バグを発見しやすい
		return result;
		// 結論：クラスが独自に管理しているメモリに対しては、不要となったオブジェクト参照にはNullを設定する。（メモリリークに気を配るべき）
		// この例でいうと、popしてsize以上のインデックスのelements配列にあるオブジェクトが不要なのは、
		// プログラマしか知らず、GCの対象にはならない。
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
