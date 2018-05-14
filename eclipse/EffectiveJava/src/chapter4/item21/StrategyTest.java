package chapter4.item21;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
/*
 * 項目２１
 * 戦略を表現するために関数オブジェクトを使用する
 */
public class StrategyTest {

	public static void main(String args[]){

		String[] stringArray = {"aaaaaa", "gggggg"};

		// 自作したコンパレータクラスの使用
		// 絶対使い方違うと思う。
		int ret = StringLengthComparator.INSTANCE.compare("aaaa", "bbbbb");
		System.out.println(ret);

		// 具象戦略クラスは無名クラスを使用して宣言されることが多い。
		// しかし、無名内部クラスでの宣言は実行されるたびにインスタンスを生成する。
		// 繰り返し実行するのであれば、private static finalのフィールドに保存しての再利用を検討する
		Arrays.sort(stringArray, new Comparator<String>(){
			public int compare(String s1, String s2){
				return s1.length() - s2.length();
			}
		});

		// フィールドに保存して再利用
		Arrays.sort(args, StringLengthComparatorField);
		Arrays.sort(args, StringLengthComparatorField);
		Arrays.sort(args, StringLengthComparatorField);
		Arrays.sort(args, StringLengthComparatorField);

		// 具象クラス（not public版）の使用
		Host.STRING_LENGTH_COMPARATOR.compare("aaaaa", "bb");

	}

	// フィールドに保存して再利用
	private static final Comparator<? super String> StringLengthComparatorField = new Comparator<String>(){
		public int compare(String s1, String s2){
			return s1.length() - s2.length();
		}
	};

}

/*
 * 具象戦略を提供する
 * こちらはHostクラスのprivateのネストしたクラスとして提供している
 * StringクラスはCASE_INSENSITIE_ORDERフィールドを通じて大文字小文字を区別しない文字列コンパレータを提供しているらしい
 */
class Host {
	private static class StrLenCmp implements Comparator<String>, Serializable {
		public int compare(String s1, String s2){
			return s1.length() - s2.length();
		}
	}

	// 返されたコンパレータはシリアライズ可能
	public static final Comparator<String> STRING_LENGTH_COMPARATOR = new StrLenCmp();

}