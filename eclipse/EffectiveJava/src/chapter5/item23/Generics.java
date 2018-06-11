package chapter5.item23;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * 項目２３
 * 新たなコードで原型を使用しない
 * 原型はジェネリクス登場以前の互換性を保つために提供されているものであり、今後新規に使用してはいけない
 * 既存のコードが全て性能なままでかつジェネリクスを使用した新たなコード外相互運用が考えられている。
 * 通常の型を使用するように設計されていたメソッドに対して、パラメータ化された型のインスタンスを渡すことも、
 * その逆も正当でなければならなかった。（移行互換性）
 */
public class Generics {

	// 原型の例
	// 今日では原型のコレクション - これはやってはいけない
	/** 私の切手コレクション。Stampインスタンスだけを含んでいる */
	private static final Collection stamps = new ArrayList();
	public static void main(String[] args){

		// 切手コレクションへのコインの誤った挿入
//		stamps.add(new Coin());
		// 原型のイテレータ - これはやってはいけない
		for(Iterator i = stamps.iterator();i.hasNext();){
			Stamp s = (Stamp) i.next(); // ClassCastExceptionがスローされる
			// Stampで何かする・・・
			// 原型だとなんでも格納できてしまうため、取り出すまでエラーとならない
			// ⇒コンパイルでエラーを検知できない
		}
		Generics g = new Generics();
		g.doSomething();
		g.example();
		g.forExample();
		Set<String> ss = new HashSet<String>();
		ss.add("ddddd");
		g.insSample(ss);
	}

	// ジェネリクスを使う
	// パラメータ化されたコレクション型 - 型安全
	private final Collection<Stamp> gStamps = new ArrayList<Stamp>();
//	private void add(Coin c){gStamps.add(new Coin())} // これはコンパイルエラーとなる。

	/*
	 * パラメータ化されたコレクションはコンパイル時に誤りを検知できる
	 */
	private void doSomething(){
		// パラメータ化されたコレクションに対するfor-eachループ - 型安全
		for(Stamp s: gStamps){
			System.out.println(s);
			// Stampで何かする・・・
			// 手作業でのキャストは不要となり（コンパイラが隠れたキャストを挿入してくれる）、
			// コンパイラがキャストを成功する事を保証する
		}
		// 従来のforループも可能
		for(Iterator<Stamp> i = gStamps.iterator();i.hasNext();){
			Stamp s = i.next(); // キャスト無で可能
			System.out.println(s);
			// Stampで何かする・・・
		}
	}

	/* 以降、ジェネリクスを使用した場合の型安全性を検証。*/
	private void example(){
		// 原型を使用しているため、実行に失敗
		List<String> strings = new ArrayList<String>();
		unsafeAdd(strings, new Integer(42));
//		String s = strings.get(0); // コンパイルは成功するが、実行時に失敗する。
//		System.out.println(s);
	}

	/*
	 * 型安全性をもたない危険なメソッド
	 * コンパイル時に、無検査呼び出しの警告が出る
	 */
	private static void unsafeAdd(List list, Object o){
		list.add(o);
	}

	/*
	 * 要素の型がわからず、型をきにしないコレクションに対しても原型はだめ。
	 * 例：２つのセットを受け取り、共通の要素の数を返すメソッド
	 * 不明な方に対する原型の使用。 - これを行ってはいけない。
	 */
	static int numElementsIncommon(Set s1, Set s2){
		int result = 0;
		for(Object o1: s1){
			if(s2.contains(o1)){
				result++;
			}
		}
		return result;
	}

	/*
	 * 上記の場合、java1.5以降は、非境界型ワイルドカード「？」を使用する。
	 * 非境界型ワイルドカード型 - 型安全で柔軟
	 */
	static int gNumElementsIncommon(Set<?> s1, Set<?> s2){
		int result = 0;
		for(Object o1: s1){
			if(s2.contains(o1)){
				result++;
			}
		}
		System.out.println(String.format("gNum result: %d", result));
		return result;

	}

	/** gの方使ってみた*/
	private void forExample(){
		Set<String> s1 = new HashSet<String>();
		s1.add("aaa");
		s1.add("bbb");

		Set<String> s2 = new HashSet<String>();
		s2.add("bbb");
		s2.add("ccc");
		Generics.gNumElementsIncommon(s1, s2);

		// ワイルドカード型のコレクションは、null以外の要素を追加できない
		Set<?> s3 = new HashSet<String>();
//		s3.add("ss"); // コンパイルエラー
		s3.add(null); // これは大丈夫

	}

	//=====================================
    // 原型を使う例外２つ
	// 　⇒どちらもジェネリクス型所法が実行時に消されていることからきている
	//=====================================
	/*
	 * １．クラスリテラル
	 * 　　　⇒○：List.class、int.class
	 * 　　　⇒×：List<String>.class、List<?>.class
	 * ２．instanceof演算子
	 * 　　　⇒下を参照
	 * 原型の正当な使用 - instanceof演算子
	 */
	private void insSample(Object o){
		if(o instanceof Set){ // 原型
			Set<?> m = (Set<?>) o; // ワイルドカード型。このキャストを忘れずに
			System.out.println(String.format("size: %d", m.size()));
		}
	}
}

class Stamp{}
class Coin{}