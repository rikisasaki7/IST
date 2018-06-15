package chapter5.item27;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chapter5.item27.GenericsMethod.Comparable;

/*
 * 項目２７
 * ジェネリックメソッドを使用する
 *
 * staticユーティリティメソッドは特に良い候補
 */
public class GenericsMethod {

	/**
	 * まずはNGから
	 * 原型を使用 - 受け入れられない！
	 */
	public static Set union(Set s1, Set s2){
		Set result = new HashSet(s1);
		result.addAll(s2);
		return result;
	}

	/*
	 * 続いてジェネリックメソッド
	 */
	public static <E> Set<E> unionG(Set<E> s1, Set<E> s2){
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}

	/** ジェネリックメソッドのテスト */
	private void genericMethodTest(){
		Set<String> guys = new HashSet<String>(Arrays.asList("Tom", "Dick", "Harry"));
		Set<String> stooges = new HashSet<String>(Arrays.asList("Larry", "Moe", "Curly"));
		Set<String> aflCio = unionG(guys, stooges); // unionGの引数はSet<String>であると型推論が働くため、
		                                            // 型パラメータの値を指定する必要がない。
		System.out.println(aflCio);

		// なお、ジェネリックコンストラクタ呼び出しは明示的に型パラメータの値を指定する必要あり
		// コンストラクタでのパラメータ化された型のインスタンスを生成
		Map<String, List<String>> anagrams = new HashMap<String, List<String>>(); // java1.7はダイヤモンド演算子が導入されたため、不要

		// この重複を削除するため、個々のコンストラクタに対応するジェネリックstaticファクトリメソッドを作成するとよい。
		// 下のnewInstance()を使用するとこう
		// staticファクトリメソッドでのパラメータ化された型のインスタンス生成
		Map<String, List<String>> gAnagrams = newInstance();

	}

	/** ジェネリックstaticファクトリメソッド */
	public static<K, V> HashMap<K, V> newInstance(){
		return new HashMap<K, V>();

	}

	// ======================================================================
	// 関連パターン：ジェネリックシングルトンファクトリー
	// 　⇒指定された型を気にせず、シングルトンのオブジェクトを生成するファクトリ
	// 　⇒Collections.reverseOrder等の関数オブジェクトに対してもよくつかわれる
	// ======================================================================
	/**
	 * ジェネリックシングルトンファクトリパターン
	 * 何らかの型Tの値を受け取り、その型の値を返す関数を記述するインタフェース
	 */
	public interface UnaryFunction<T>{ T apply(T arg); }

	/**
	 * ジェネリックシングルトンファクトリパターン
	 * 恒等関数をイメージ。状態を持たないので同様の型のオブジェクトを都度生成する事は不要。
	 * ジェネリックスはイレイジャ実装のため、１つのジェネリックシングルトンだけで大丈夫。
	 */
	private static UnaryFunction<Object> IDENTITY_FUNCTION =
		new UnaryFunction<Object>(){
			public Object apply(Object arg) { return arg; }
		};

	/*
	 * IDENTITY_FUNCTIONは状態を持たなく、その型パラメータは非境界のため、
	 * 全ての型に対して１つのインスタンスを共有するのは安全
	 */
	@SuppressWarnings("unchecked")
	public static <T> UnaryFunction<T> identityFunction(){
		return (UnaryFunction<T>) IDENTITY_FUNCTION;

		// IDENTITY_FUNCTIONの戻り値、UnaryFunction<Object>はUnaryFunction<T>とは異なるため、
		// 無検査キャストの警告が発生。
		// しかし、恒等関数のため、ステートレスかつ引数を変更なく返すので、どのようなTの値でも
		// UnaryFunction<T>として使用する事は安全となる。
	}

	/**
	 * シングルトンファクトリパターンのテスト
	 * String型とNumber型でジェネリックシングルトンファクトリを使用してみる
	 */
	private void genericsSingletonTest(){

		// String型
		String[] strings = {"jude", "hemp", "nylon"};
		UnaryFunction<String> sameString = identityFunction();
		for(String s: strings){
			// キャストを行わず、エラー・警告を出さずにプログラムできている
			System.out.println(String.format("applyResult: %s", sameString.apply(s)));
		}

		// Number型
		Number[] numbers = {1, 2.0,3L};
		UnaryFunction<Number> sameNumber = identityFunction();
		for(Number n: numbers){
			System.out.println(sameNumber.apply(n));
		}
	}


	// ======================================================================
	// 稀な関連パターン：再帰型境界
	// 　⇒型パラメータがその型パラメータ自身が関係する何らかの式で制限される。
	// 　⇒最もよくあるのは、Comparableインタフェース
	// ======================================================================
	public interface Comparable<T>{ int compareTo(T o); }

	/*
	 * 型パラメータTは、Comparable<T>を実装している型の要素と比較可能な型を定義します。
	 * 実際にはほとんどは自分自身の型が比較可能。
	 * リストをソートしたり、検索したり、最小値／最大値を求めるなどしたければ、
	 * リスト内の個々の要素がその他の要素と比較可能である、「相互比較可能」である必要がある。
	 *
	 * 相互比較可能性を表現するために再帰型境界を使用する
	 */
	public static <T extends Comparable<T>> T max(List<T> list){
		Iterator<T> i = list.iterator();
		T result = i.next();
		while(i.hasNext()){
			T t = i.next();
			if(t.compareTo(result) > 0){
				result = t;
			}
		}
		return result;
	}

	/** 再帰型境界のテストメソッド */
	private void borderTest(){
		List<Test> testList = new ArrayList<Test>();
		testList.add(new Test("abc"));
		testList.add(new Test("def"));
		testList.add(new Test("ddfddddddddddddddddddddd"));
		Test maxT = max(testList);
		System.out.println(String.format("maxT.base: %s", maxT.getBase()));

	}


	/** メインメソッド */
	public static void main(String[] args){
		GenericsMethod g = new GenericsMethod();
		g.genericMethodTest();
		g.genericsSingletonTest();
		g.borderTest();
	}
}

/**
 * 再帰型境界のテスト用クラス
 * 自分自身と比較可能なよう定義するため、Comparable<Test>を実装する。
 */
class Test implements Comparable<Test>{
	private String base = "aaaaaaaa";

	@Override
	public int compareTo(Test s){
		int result = 0;
		if(this.getBase().length() > s.getBase().length()){
			result = 1;
		} else if(this.getBase().length() < s.getBase().length()){
			result = -1;
		}
		return result;
	}

	/** コンストラクタ */
	public Test(String base){ this.base = base; }
	/** アクセッサー */
	public String getBase(){ return this.base; }

}