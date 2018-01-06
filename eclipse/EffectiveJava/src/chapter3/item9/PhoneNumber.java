package chapter3.item9;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riki
 * 項目９
 * equalsをオーバーライドするときは、常にhashCodeをオーバーライドする
 * hashCode契約
 * １．hashCode メソッドの値は、equals で利用する個々のプロパティが変更されない限り、同一アプリケーション実行で同じ値を返す必要がある。
 *   　　なお、この値は別のアプリケーション実行に対しては首尾一貫する必要はない
 * ２．２つのオブジェクトに対する equals による比較が等しければ、２つのオブジェクトの hashCode 呼び出しは同じ整数結果を返さなければならない。
 * ３．２つのオブジェクトに対する equals による比較が等しくなければ、２つのオブジェクトの hashCode 呼び出しの整数結果が異なる必要はない。
 * 　　　しかし、equals で等しくないオブジェクトに同じ整数結果を返すことはハッシュテーブルのパフォーマンスを大幅に低下させることを覚えておく。
 */
public final class PhoneNumber {

	/** 市外局番 */
	private final short areaCode;
	/** 市内局番 */
	private final short prefix;
	/** 加入者番号 */
	private final short lineNumber;
	
	/**
	 * デフォルトコンストラクタ
	 * インスタンス生成前に番号の範囲妥当性チェックを行います。
	 * @param areaCode 市外局番
	 * @param prefix 市内局番
	 * @param lineNumber 加入者番号
	 */
	public PhoneNumber(int areaCode, int prefix, int lineNumber) {
		rangeCheck(areaCode, 999, "area code");
		rangeCheck(prefix, 999, "prefix");
		rangeCheck(lineNumber, 9999, "line number");
		this.areaCode = (short)areaCode;
		this.prefix = (short)prefix;
		this.lineNumber = (short)lineNumber;
	}
	
	/**
	 * 電話番号の各番号について数値範囲チェックを行います。
	 * @param arg チェック対象の電話番号
	 * @param max 範囲
	 * @param name 区画
	 */
	private static void rangeCheck(int arg, int max, String name) {
		if(arg < 0 || arg > max) throw new IllegalArgumentException(name + ": " + arg);
	}
	
	/**
	 * PhoneNumberクラスの意味のあるフィールドを使用して比較equalsメソッド
	 * @param o 比較対象のオブジェクト
	 * @return 等しいか田舎
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this) return true; // 自分と等しければtrueを返す
		if(!(o instanceof PhoneNumber)) return false; // nullの場合や、PhoneNumberクラスのインスタンスの場合はfalseを返す
		PhoneNumber pn = (PhoneNumber) o; // instance ofで検査をしたので、安心してキャスト可能
		return pn.lineNumber == lineNumber && // 比較の順番はもっとも違いが出やすいフィールドからの方がパフォーマンス的に望ましい
				pn.prefix == prefix &&
				pn.areaCode == areaCode;
	}
	
	/** mainメソッド */
	public static void main(String args[]){
		Map<PhoneNumber, String> m = new HashMap<PhoneNumber, String>();
		m.put(new PhoneNumber(707, 867, 5309), "Jenny");
		System.out.println(m.get(new PhoneNumber(707, 867, 5309))); // hashCodeをオーバーライドしていないと、これがnullになる
																	// ２つのオブジェクトは異なるhashCode値を持つため。
		
	}

	/**
	 * ObjectのhashCodeをオーバーライド
	 * 良いhashCodeメソッドは、等しくないオブジェクトに対しては等しくないハッシュコードを返却する
	 * hashCodeの計算に使用するフィールドは以下規則で決定すること
	 * ・オブジェクトの意味のあるフィールドを使用してhashCodeを計算する
	 * ・計算できるフィールドは除外してOK
	 * ・equals比較で使用されていないフィールドは必ず除外する
	 * 
	 * 今回は意味のあるフィールドがshortなので、intに置き換えて計算している。
	 * オブジェクト参照でequalsメソッドが再帰的にequalsを呼び出して比較しているならば再帰的にhashCodeを呼び出す
	 * 複雑な計算が必要ならば、正規形を計算してその正規形に対してhashCodeを呼び出す
	 * そのフィールドがnullならば0を返す（定数ならなんでもよいが0が伝統的）
	 * 配列ならば、各要素を別々のフィールドとみなし、全ての要素に対するhashCodeを計算する
	 */
	@Override
	public int hashCode() {
		int result = 17; // この数字は任意
		result = 31 * result + areaCode; // 31も任意だが機数の素数であることが
		result = 31 * result + prefix;   // 他にも乗算をシフト演算にJVMが自動で置き換えるらしい（31 * i == (i << 5) - i） <-意味はわかっていない
		result = 31 * result + lineNumber;
		return result;
	}
//	/**
//	 * 最悪なhashCodeメソッド
//	 * どのオブジェクトでも同一の値を返す
//	 */
//	@Override
//	public int hashCode() { return 42;}
}
