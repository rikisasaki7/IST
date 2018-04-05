package chapter4.item11;

/**
 * @author Riki
 * 項目１１
 * cloneを注意してオーバーライドする
 * １.Cloneableインタフェースはもともとクラスのオブジェクトが複製を許可している事を示すためのものだったが、
 * 　失敗しているため、使わない方が良い。
 *
 * ２.Cloneableインタフェースはcloneメソッドを持っていない
 * ー＞Cloneableインタフェースは実装することは避けた方が良い。言語外のルールを満たす必要があり、使いづらい
 * ー＞実装するならば、publicの適切に機能するcloneメソッドを提供する必要あり。
 * ー＞finalではないクラスでcloneメソッドをオーバーライドするならば、super.cloneにより
 * 　　得られたオブジェクトを返すべき
 * ー＞PhoneNumberクラス参照
 *
 * ３.Cloneableインタフェースは、Objectクラスのprotectedのclone実装の振る舞いを決定する
 * ー＞実装している：複製対象のフィールド値が同じオブジェクトを返す（コンストラクトを経由しない）
 * ー＞実装していない：CloneNotSupportedExceptionを発生させる
 *
 */
public class CloneSample implements Cloneable {

	public static void main(String[] args) {
		// cloneオブジェクトの振る舞いテスト
		CloneSample s = new CloneSample();
		try{
			// Cloneableを実装していないと、clone()でCloneNotSupportedExceptionが発生　ー＞　これが３
			System.out.println(String.format("s.clone() != s -> %b", s.clone() != s));
			System.out.println(String.format("s.clone().getClass() == s.getClass() -> %b", s.clone().getClass() == s.getClass()));
			System.out.println(String.format("s.clone().equals(s) -> %b", s.clone().equals(s)));
		} catch(CloneNotSupportedException c){
			System.out.println(c.getStackTrace());
		} catch(Exception e){
			System.out.println(e.getStackTrace());
		}

	}
}

/*
 * クラスのフィールドが不変フィールドのみの場合
 */
class PhoneNumber implements Cloneable {
	
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
		this.areaCode = (short)areaCode;
		this.prefix = (short)prefix;
		this.lineNumber = (short)lineNumber;
	}
	
	/*
	 * Cloneableを実装したときの、publicなcloneメソッド
	 * ー＞書いてはいるが、そもそもCloneableインタフェースが望ましくないから、あまり意味はない
	 * ー＞これが２
	 */
	@Override
	public PhoneNumber clone(){
		try{
			// superクラス（オブジェクト）のcloneをよんでいる。
			// 共変戻り値が許されているので、ObjectのサブクラスであるPhoneNumberをreturnしても良い
			// API側でできることは、クライアントにさせない方が良い
			return (PhoneNumber) super.clone(); 
		} catch(CloneNotSupportedException e){
			throw new AssertionError(); // 起こるはずがない
		}
	}
}