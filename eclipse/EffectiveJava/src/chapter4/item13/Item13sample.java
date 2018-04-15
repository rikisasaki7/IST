package chapter4.item13;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Riki
 * 項目１３
 * クラスとメンバーへのアクセス可能性を最小限にする
 * モジュール間の通信は可能な限り後悔しているAPIどうしのみで行う
 * 各クラスやメンバーをできる限りアクセス不可にするべき
 * 
 * トップレベルのクラスは、publicより可能ならパッケージプレイベートにして、パッケージの内部構造に止める
 * ー＞そうすると外部公開されないので、リリース後も好きに修正できる。使用しているクライアントにする必要がない
 * ー＞仮にパッケージプライベートのクラスを利用しているクラスが１つだけなら、privateの内部クラスを検討する
 * 
 * publicクラスのメンバーについては、protectedもクラスの公開APIの一部となるため、クライアントの事をきにする必要がある
 * インスタンスフィールドは決してpublicにしてはいけない。好きに値を代入されてしまうので、トンデモなことになる
 * 配列はfinalにしたとしても、内部の値更することができることに注意
 */
public class Item13sample {

	// 潜在的なセキュリティホール
	public static final int[] VALUES = {1, 2, 4};
	// セキュリティホールテスト用内部クラス
	class InnerSample{
		void test(){
			// 変更前
			print(VALUES);
			// 配列の中身を変えてみる
			Item13sample.VALUES[2] = 3;
			// 変更後
			print(VALUES);
		}
		void print(int[] i){
			System.out.println("---------");
			for(int j: i){
				System.out.print(j);
			}
			System.out.println();
		}
	}
	
	/*
	 * mainメソッド
	 */
	public static void main(String[]args){
		Item13sample sam = new Item13sample();
		Item13sample.InnerSample in = sam.new InnerSample();
		in.test(); // 初期化時の「124」から、「123」に上書きされている。finalな配列なのに！
	}

	// 上記ホールの解決策その１
	// publicの配列をprivateにし、publicの不変リストを追加する
	private static final String[] PRIVATE_VALUES = {"a","b","c"};
	public static final List<String> RESOLVE_ONE_VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
	
	// 上記ホールの解決策その２
	// 配列をprivateにして、privateの配列を返すpublicなメソッドを追加する
	private static String[] PRIVATE_VALUES_TWO = {"a", "b", "c"};
	public static final String[] vlaues(){
		return PRIVATE_VALUES_TWO.clone();
	}
	
	// １と２の違い：戻り値の型。一方はリスト、一方は配列
	// クライアントが何をしたいか、どのような形で提供をしたいか考えて決定する。
}