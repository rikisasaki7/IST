package chapter5.item24;

import java.util.Arrays;

/*
 * 項目２４
 * 無検査警告を取り除く
 * ジェネリクスの際によくみる無検査警告は可能な限り必ず取り除く
 * 　　⇒ClassCastExceptionが発生しない確信が増す
 * ＊無検査キャスト
 * ＊無検査メソッド呼び出し
 * ＊無検査ジェネリック配列生成
 * ＊無検査変換
 *
 * 警告を取り除くことができず、コードが型安全だと明確に示すことができれば
 * @SuppressWarnings("unchecke")を付与する
 * 　⇒アノテーションはどの粒度でもつける事ができるが、必ず最小の単位となるようにつける
 * 　⇒もしくはつける事ができるようにコードを修正する
 * 　⇒アノテーションを付けるときは、型安全である理由を必ずコメントで残す
 */
public class RemoveWarning {

	/** ArrayListのコードを模倣して変数宣言 */
	private Object[] elements;
	private int size;

	/*
	 * ArrayListのコードを模倣
	 * このままだと、一つ目のreturnで無検査キャスト警告がでる
	 * message: 型の安全性: Object[] から T[] への未検査キャスト
	 */
	public <T> T[] toArray(T[] a){
		if(a.length < size)
			return (T[]) Arrays.copyOf(elements, size, a.getClass());
		System.arraycopy(elements, 0, a, 0, size);
		if(a.length > size)
			a[size] = null;
		return a;
	}

	/*
	 * 上記のtoArrayから警告を取り除くとこうなる
	 * ★重要★
	 * @SuppressWarningsを付与するときには、安全である理由を述べるコメントを追加する事
	 * ★重要★
	 */
	public <T> T[] nonWarnToArray(T[] a){
		if(a.length < size){
			// T[]として渡されたものと同じ型の配列を
			// 生成するのでこのキャストは正しい
			@SuppressWarnings("unchecked")
			T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());
			return result;
		}
		System.arraycopy(elements, 0, a, 0, size);
		if(a.length > size)
			a[size] = null;
		return a;
	}
}
