package chapter4.item21;

/*
 * 項目２１
 * 戦略を表現するために関数オブジェクトを使用する
 * ・関数オブジェクト：
 * 　プログラムが特定の関数を保存してその関数を呼び出せるように受け渡すことができる
 * 　Ｊａｖａにおいてはオブジェクトを経由して関数を呼び出すことができる。
 * 　オブジェクトに対するメソッドは一般にそのオブジェクトに対する捜査を行うが、
 * 　他のオブジェクトに対して操作を行うメソッドを持つオブジェクトを定義できる。
 * 　オブジェクトは明示的にそのメソッドに渡される。
 * 　そのようなメソッドを１つだけ公開するクラスのインスタンスは関数オブジェクトと呼ばれる。
 *
 * 以下は関数オブジェクトを表すクラスの例である。
 * 文字列の長さを比較して順序付けるコンパレータクラス。
 * これらは文字列比較に対する具象戦略となる。
 * また、状態を持たないステートレスなクラスとなる。
 *
 * <code>Comparator</code>戦略インタフェースを実装する
 */
public class StringLengthComparator implements Comparator<String> {

	/* インスタンスを生成不可にし、メモリの節約（ステートレスだからシングルトンで大丈夫） */
	private StringLengthComparator(){}

	/* シングルトンオブジェクト */
	public static final StringLengthComparator INSTANCE = new StringLengthComparator();

	/*
	 * 文字列の長さを基にした順序を返却するメソッド
	 * @see chapter4.item21.Comparator#compare
	 */
	public int compare(String s1, String s2){
		return s1.length() - s2.length();
	}

}
