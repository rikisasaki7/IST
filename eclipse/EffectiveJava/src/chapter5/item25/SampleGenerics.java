package chapter5.item25;

import java.util.ArrayList;
import java.util.List;

/*
 * 項目２５
 * 配列よりリストを選ぶ
 * 配列とリストの違いの内、大きいものは以下２つ
 * １．配列：共変、リスト：不変
 * 　　⇒共変：SubがSuperのサブタイプならば、List<Sub>はList<Super>のサブタイプとなる
 * 　　⇒不変：SubがSuperのサブタイプだとしても、List<Sub>はList<Super>のサブタイプではない
 * ２．配列：具象化されている、リスト：イレイジャ実装
 * 　　⇒具象化されている：実行時にその要素型を強制する。以下の例のようにLong配列にStringを入れようとすると
 * 　　　実行時にArrayStoreExceptionが発生する
 * 　　⇒イレイジャ実装：コンパイル時にのみ型制約を強制し、実行時には型制約を廃棄する
 * 　　　　⇒イレイジャにより、原型とジェネリクスの互換性を保っている
 *
 * 上記により、以下の様なジェネリクス型配列を生成する事は許可されていない
 * 　＊ジェネリック型の配列
 * 　＊パラメータ化された型の配列
 * 　＊型パラメータの配列
 *
 * E、List<E>、List<String>は具象化不可能型と言われる。実行時の表現がコンパイル時よりも情報が少ないという意味となる。
 *
 * ジェネリック配列生成エラーとなる場合の代替案は、ほぼList<E>を使用する事で解決する。
 */
public class SampleGenerics {

	/*
	 * １のサンプルを実装
	 * 共変と不変
	 */
	private void sampleMethod(){
		// １のサンプル（共変）
		// 実行時に失敗する
		Object[] objectArray = new Long[1];
		objectArray[0] = "String insert";

		// ２のサンプル
		// コンパイルでエラー　⇒　１より型安全
//		List<Object> ol = new ArrayList<Long>(); // ←ここでコンパイルエラー
//		ol.add("String insert");
	}

	/*
	 * ジェネリクスを使用した配列の生成を許可されていない理由のサンプル
	 * 型安全でなくなり、実行時にClassCastExceptionを起こしてしまう
	 */
	private void castException(){
//		List<String>[] stringLists = new List<String>[1]; // ←コンパイルエラー
//		List<Integer> intList = Arrays.asList(42);
//		Object[] objects = stringLists;
//		objects[0] = intList;
//		String s = stringLists[0].get(0); // ←stringsLists[0]にはintListが格納されているため、ClassCastExceptionが発生
	}

	/*
	 * ジェネリック配列の代替案としてList<E>を使用するサンプル
	 * まずは、ジェネリクスを使用しない簡約（applyをかます）、並行性の欠陥（並行処理できない）
	 */
	static Object reduce(List list, Function f, Object initVal){
		synchronized(list){
			Object result = initVal;
			for(Object o: list){
				result = f.apply(result, o);
			}
			return result;
		}
	}
	interface Function{
		Object apply(Object arg1, Object arg2);
	}

	/*
	 * 続いてジェネリクスを用いずに並行性の欠陥もない簡約（ジェネリクスがないときはこうしていた）
	 */
	static Object reduce2(List list, Function f, Object initVal){
		Object[] snapshot = list.toArray(); // リストを内部的にしてコピー時にロック（項目６７で説明）
		Object result = initVal;
		for(Object o: snapshot){
			result = f.apply(result, o);
		}
		return result;
	}

	/** ジェネリクスを使用 */
	interface FunctionG<T>{
		T apply(T arg1, T arg2);
	}

	/** ジェネリクスを使用したインタフェースの実装クラス */
	class FunctionGString implements FunctionG<String>{
		public String apply(String arg1, String arg2){
			return arg1 + arg2;
		}
	}

	/*
	 * 単純にジェネリクスを適用させようとするとこうなるが、
	 * コンパイルエラーとなる
	 */
	static <E> E reduceG(List<E> list, FunctionG<E> f, E initVal){
//		E[] snapshot = list.toArray(); // リストを内部的にロック
									   // ここでコンパイルエラー
									   // メッセージ：型の不一致: Object[] から E[] には変換できません
		E[] snapshot = (E[]) list.toArray(); // こうすると無検査キャスト警告
											 // メッセージ：型の安全性: Object[] から E[] への未検査キャスト

		// ====================================================================================
		// 実行時にEが何であるかコンパイラは判断できないため、実行時キャストの安全性を検査できないため、
		// 上記のキャストだけでは型安全ではない。
		// なぜならsnapshotのコンパイル時の型はE[]型であり、EはStringだったりIntegerだったりするが、
		// Eの実行時の型はObjectとなるため、危険。（デバッグしたら本当にObject型だった。）
		// 具象化不可能型の配列へキャストするのは、特殊なケースのみにとどめるべき（項目２６）
		// ====================================================================================

		E result = initVal;
		for(E e: snapshot){
			result = f.apply(result, e);
		}
		return result;
	}

	/*
	 * 配列の代わりにリストを使用し、上記の問題を取り除いたバージョン
	 * リストに基づく簡約
	 */
	static <E> E reduceG2(List<E> list, FunctionG<E> f, E initVal){
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


	private boolean flg = false;
	/** メインメソッド */
	public static void main(String[] args){
		SampleGenerics s = new SampleGenerics();
		if(s.flg) s.sampleMethod(); // 実行時エラーでるから動かないようにする
		s.castException();
		List<String> sList = new ArrayList<String>();
		sList.add("aaaaa");
		sList.add("bbbbb");
		String retS = SampleGenerics.reduceG(sList, s.new FunctionGString(), "");
		System.out.println(String.format("reduceG.result: %s", retS));

	}
}
