package chapter2.item7;

/**
 * @author Riki
 * 項目７
 * ファイナライザを避ける
 * ※一応参考：
 * https://qiita.com/jkr_2255/items/429648970ee104998d2a
 * ファイナライザはJVMが自動でGCした際に実行されるため、いつ実行されるかの保証がない。
 * そのため、時間的に制約があることをファイナライザでするべきではない。
 * なお、ファイナライザの正当な使い方は以下２点
 * １.明示的終了メソッドをクライアントが呼び忘れた際の安全ネット＜ー警告を出力すべき
 * ２.ネイティブピアを持つオブジェクトのGC
 * ※ネイティブピアとは：
 * http://hjm333.hatenablog.com/entry/2015/08/19/234202
 * 　ー＞ネイティブピアと呼ばれる、通常のオブジェクトがネイティブメソッドを通して処理を行うネイティブオブジェクトに対しての利用である。
 * https://www.eeb.co.jp/wordpress/?p=476
 * 　ー＞このキーワードは native修飾子といい、メソッドがネイティブメソッドであることを示します。
 * 　　　ネイティブメソッドとはメソッドがJavaではない他の言語で実装されているメソッドのことです。 
 */
public class Foo extends Foo2 {
	// ファイナライズガーディアンで使うためのインスタンス
	private Foo priFoo = new Foo();
	/**
	 * mainメソッド
	 */
	public static void main(String args[]) {
		Foo2 foo2 = new Foo2();
		// 終了メソッドの実行を保証するtry-finallyブロック
		// finalizeに任せるのではなく、こちらにする。
		// なお、java1.7からはtry-with-resource使える
		try {
			System.out.println(foo2);
		} finally {
			// finalyで明示的終了メソッドを呼び出す
			foo2.terminate();
		}
		// try-with-resourceの例
//		try(Foo2 foo3 = new Foo2()){
//			// 処理。このブロックが終わるとfoo3が自動で解放される
//		} catch(Exception e){
//			// 処理
//		}
	}

	/**
	 * ファイナライザ
	 * Foo2#finalizeをオーバーライド
	 * @see Foo2#finalize()
	 * @throws Throwable
	 * サブクラスでfinalizeを実行しても、スーパークラスのfinalizeは自動で実行されない。
	 * サブクラスがスーパークラスのfinalizeをオーバーライドする際、
	 * finallyでスーパークラスのfinalize処理を実行するべき
	 * ー＞サブクラスのfinalizeで例外が起きたとしても、スーパークラスのfinalizeが保証される
	 * ー＞なお、ファイナライズ内でキャッチされない例外が発生したとしても、
	 * 　　その例外は無視されてファイナライズは終了する。
	 */
	@Override
	protected void finalize() throws Throwable {
		try {
			// サブクラス（Foo）のファイナライズ処理
			System.out.println("Foo.finalize invoked!!");
		} finally {
			// スーパークラスのファイナライズ処理
			super.finalize();
		}
	}
	
	/**
	 * ファイナライズガーディアン＜ーfinalize処理を書くならこれを検討すべき
	 * 必要なfinalize処理をまとめて記述しておく。
	 * FooオブジェクトがGCされるとき、このオブジェクトもGCされ
	 * ガーディアンのインナーメソッドとして定義してあるfinalizeが実行される
	 */
	private final Object finalizerGuardian = new Object() {
		@Override
		protected void finalize() throws Throwable {
			// 外側のFooオブジェクトをファイナライズする
			// クライアントがFoo#finalizeを実行することを忘れても、必ず呼ばれる
			priFoo.finalize();
		}
	};
}

class Foo2 {
	/** Foo2の明示的終了メソッド */
	protected void terminate(){
		System.out.println("terminated!!");
	}
	
	/**
	 * @throws
	 * ファイナライザ
	 * java.lang.Objectのfinalizeをオーバーライド
	 * Objectはprotected
	 */
	@Override
	protected void finalize() throws Throwable {
		System.out.println("Foo2 finalized!!");
	}
}
	