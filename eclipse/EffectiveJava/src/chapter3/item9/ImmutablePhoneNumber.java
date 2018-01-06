package chapter3.item9;

/**
 * @author Riki
 * 項目９
 * equalsをオーバーライドするときは、常にhashCodeをオーバーライドする
 * 対象のクラスが不変であり、hashCodeを計算するコストが高い場合、オブジェクト内に
 * hashCodeをキャッシュしておくと良い。
 * しかし、オブジェクトがハッシュキーとして使用される場合はインスタンス生成時に計算するべき
 * もしくは遅延初期化を検討する
 * このクラスでは遅延初期化の方法を試してみる
 */
public class ImmutablePhoneNumber {

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
	public ImmutablePhoneNumber(int areaCode, int prefix, int lineNumber) {
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
		if(!(o instanceof ImmutablePhoneNumber)) return false; // nullの場合や、PhoneNumberクラスのインスタンスの場合はfalseを返す
		ImmutablePhoneNumber pn = (ImmutablePhoneNumber) o; // instance ofで検査をしたので、安心してキャスト可能
		return pn.lineNumber == lineNumber && // 比較の順番はもっとも違いが出やすいフィールドからの方がパフォーマンス的に望ましい
				pn.prefix == prefix &&
				pn.areaCode == areaCode;
	}
	
	// 遅延初期化、キャッシュされたhashCode
	private volatile int hashCode;
	
	/**
	 * ObjectのhashCodeをオーバーライド
	 * 
	 */
	@Override
	public int hashCode() {
		int result = hashCode;
		if(result == 0) {
			result = 17;
			result = 31 * result + areaCode; // 31も任意だが機数の素数であることが
			result = 31 * result + prefix;   // 他にも乗算をシフト演算にJVMが自動で置き換えるらしい（31 * i == (i << 5) - i） <-意味はわかっていない
			result = 31 * result + lineNumber;
		}
		return result;
	}	
}
