package chapter3.item11;

import java.util.ArrayList;

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
 * 結論としては、cloneをうまく機能するようにするより、オブジェクトコピーの代替手段を用意する方が良い。
 * コピーコンストラクタか、コピーファクトリーを作ること。
 */
public class CloneSample implements Cloneable {

	private int primitiveField;
	private int[] arrayField = {1, 2, 3};

	public static void main(String[] args) {
		// cloneオブジェクトの振る舞いテスト
		CloneSample s = new CloneSample();
		// Cloneableを実装していないと、clone()でCloneNotSupportedExceptionが発生　ー＞　これが３
		System.out.println(String.format("s.clone() != s -> %b", s.clone() != s));
		System.out.println(String.format("s.clone().getClass() == s.getClass() -> %b", s.clone().getClass() == s.getClass()));
		System.out.println(String.format("s.clone().equals(s) -> %b", s.clone().equals(s)));
	}

	/*
	 * Cloneableを実装するクラスは、戻り値型がそのクラス自身であるpublicのcloneメソッドをオーバーライドすうべき
	 * その中では、まずsuper.cloneをオーバーライドするべき
	 * その後、修正すべきフィールドを深くコピーするべき
	 * @see java.lang.Object#clone()
	 */
	public CloneSample clone(){
		try{
			CloneSample o = (CloneSample) super.clone(); // まずsuper.cloneを呼び出す
			o.arrayField = arrayField.clone(); // 配列はcloneを使用
			return o;
		} catch(CloneNotSupportedException e){
			throw new AssertionError();
		}

	}

	/**
	 * @return primitiveField
	 */
	public int getPrimitiveField() {
		return primitiveField;
	}

	/**
	 * @return arrayField
	 */
	public int[] getArrayField() {
		return arrayField;
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

	/**
	 * @return areaCode
	 */
	public short getAreaCode() {
		return areaCode;
	}

	/**
	 * @return prefix
	 */
	public short getPrefix() {
		return prefix;
	}

	/**
	 * @return lineNumber
	 */
	public short getLineNumber() {
		return lineNumber;
	}
}

/*
 * コピーコンストラクタ（変換コンストラクタ）とコピーファクトリ（変換ファクトリ）のサンプルクラス
 */
class MutablePhone {
	private int a;
	private int b;
	private String c;
	private CloneSample cs;
	private ArrayList<String> arrayList = new ArrayList<String>();

	/*
	 * デフォルトコンストラクタ
	 */
	public MutablePhone(int a, int b, String c, CloneSample cs, ArrayList<String> af){
		this.a = a;
		this.b = b;
		this.c = c;
		this.cs = cs;
		this.arrayList = af;
	}

	/*
	 * コピーコンストラクタ
	 */
	public MutablePhone (MutablePhone mp){
		this.a = mp.a;
		this.b = mp.b;
		this.c = mp.c;
		this.cs = mp.cs.clone(); // deepCopy
		ArrayList<String> resultArray = new ArrayList<String>();
		for(String s: mp.arrayList){
			resultArray.add(s); // deepCopy
		}
	}

	/*
	 * コピーファクトリ
	 */
	public static MutablePhone newInstance(MutablePhone mp){
		MutablePhone result = new MutablePhone(mp.a, mp.b, mp.c, mp.cs.clone(), new ArrayList<String>());
		for(String s: mp.arrayList){
			result.arrayList.add(s); // deepCopy
		}
		return new MutablePhone(mp);
	}

}