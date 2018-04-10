package chapter3.item11;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * @author Riki
 * 項目１１
 * cloneを注意してオーバーライドする
 * クラスのフィールドで可変オブジェクトを参照しているフィールドが存在する場合
 * ー＞そのままcloneすると、参照先までもコピーされてしまうため、
 * 　　可変オブジェクトの中身をコピーしていかなければいけない
 * ー＞そうしないと、コピー元、コピー先どちらかの値を変えると双方の値がかわってしまう。
 */
public class Stack {

	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	public Stack(){
		this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	
	public void push(Object e){
		ensureCapacity();
		elements[size++] = e;
	}
	
	public Object pop(){
		if(size == 0){
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[size] = null; // 廃れた参照を取り除く
		return result;
	}
	
	/*
	 * 最低もう１つの要素分の容量を確保する
	 */
	public void ensureCapacity(){
		if(elements.length == size) elements = Arrays.copyOf(elements, 2 * size + 1);
	}
	
	/*
	 * 単純に全てコピーすると、elements配列の参照先をそのままコピーしてしまう。
	 * そのため、内部を再帰的にコピーするcloneメソッドを作成する。
	 */
	@Override
	public Stack clone(){
		try{
			Stack result = (Stack) super.clone();
			// 配列内部を再帰的にコピー。
			// 1.5から配列に対するcloneは同じ型の配列を返却する。そのためキャストも不要。
			// なお、elementsがfinalの場合、cloneの結果を代入できないので不可。
			result.elements = elements.clone(); 
			return result;
		} catch(CloneNotSupportedException e){
			throw new AssertionError();
		}
	}
}
