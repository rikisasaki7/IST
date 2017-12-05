package chapter2.item3;

import java.io.ObjectStreamException;

/**
 * @author Riki
 * シングルトンの実装その２
 */
public class ElvisSingleton2 {

	/** 
	 * staticファクトリメソッドによるシングルトン
	 * こちらもその１と同様にリフレクションを使用するとprivateのコンストラクタを呼び出せるらしい
	 * なお、ディシリアライズをすることによってインスタンス生成されてしまうらしい
	 * →解決法は下に記載
	 */
	public static final ElvisSingleton2 INSTANCE = new ElvisSingleton2();
	private ElvisSingleton2(){}
	public static ElvisSingleton2 getInstance(){ return INSTANCE;}
	
	/**
	 * ディシリアライズのタイミングでインスタンスを生成されない
	 * 本物のElvisSingleton2を返却して、偽物をGCに処理させる
	 * なお、このメソッドに加えて、全てのフィールドにtransientをつける必要がある。
	 * readResoleve：https://www.jpcert.or.jp/java-rules/ser01-j.html
	 */
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
}